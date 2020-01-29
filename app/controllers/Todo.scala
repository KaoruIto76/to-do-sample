/**
 *
 * to do sample project
 *
 */

package controllers

import javax.inject._
import play.api._
import play.api.mvc._

import scala.concurrent.Future
import lib.persistence.default._
import lib.model.Category
import lib.model.Todo

import model.category._
import model.todo._

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Valid
import play.api.data.validation.Constraint
import play.api.data.validation.Invalid
import play.api.i18n.I18nSupport

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class TodoController @Inject()(val controllerComponents: ControllerComponents) extends BaseController with I18nSupport {

  implicit val ec = scala.concurrent.ExecutionContext.global


  // form value
  val formData = Form(mapping(
      "categoryId" -> longNumber,
      "title"      -> text.verifying("タイトルを入力してください", {!_.isEmpty()}),
      "body"       -> text.verifying("内容を入力してください", {!_.isEmpty()}),
    )(Todo.FormValue.apply)(Todo.FormValue.unapply)
  )

  /**
   * show all todo
   */
  def showAllTodo() = Action.async { implicit req =>
    for {
      allTask     <- TodoRepository.getAll
      allCategory <- CategoryRepository.getAll
    } yield {

      val categoryMap = allCategory.map(ca => (ca.id -> ca)).toMap
      val tasks       = allTask.map(ta => (ta,categoryMap(ta.v.cid)))

      // factory view value
      val vv = ViewValueTodo(
        title  = "Todo一覧",
        tasks  = tasks,
        cssSrc = Seq("main.css","todo.css"),
        jsSrc  = Seq("main.js")
      )
      Ok(views.html.todo.Main(vv))
    }
  }

  /**
   * show add form
   */
  def showAddForm() = Action.async { implicit req =>
    for {
      allCategory <- CategoryRepository.getAll
    } yield {
      // factory view value
      val vv = ViewValueTodoAdd(
        title       = "Todo新規作成",
        allCategory = allCategory,
        cssSrc      = Seq("main.css","todo.css"),
        jsSrc       = Seq("main.js")
      )
      Ok(views.html.todo.Add(vv,formData))
    }
  }

  def add() = Action.async { implicit req =>
    formData.bindFromRequest.fold(
      errors => Future.successful(BadRequest("failed")),
      data   => {
        val entity = Todo(data.title,data.body,Category.Id(data.cid))
        for {
          _ <- TodoRepository.add(entity)
        } yield Ok("aaaaaaaaaaa")
      }
    )
  }

  /**
   * show edit form
   */
  def edit(id: Long) = Action.async { implicit req =>
    for {
      task     <- TodoRepository.get(Todo.Id(id))
      category <- task match {
        case Some(ta) => CategoryRepository.get(ta.v.cid)
        case None     => Future.successful(None)
      }
    } yield Ok(s"$category")
  }
}

