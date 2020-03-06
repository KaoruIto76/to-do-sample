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

import model.ViewValueMessage
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
class TodoController @Inject()(val controllerComponents: ControllerComponents)
  extends BaseController with I18nSupport {

  implicit val ec = scala.concurrent.ExecutionContext.global


  // フォームの値をバインド
  val formData = Form(mapping(
      "categoryId" -> longNumber,
      "title"      -> text.verifying("タイトルを入力してください", {!_.isEmpty()}),
      "body"       -> text.verifying("内容を入力してください", {!_.isEmpty()}),
      "status"     -> optional(number)
    )(Todo.FormValue.apply)(Todo.FormValue.unapply)
  )

  /**
   * TODO 一覧
   */
  def showAllTodo() = Action.async { implicit req =>
    for {
      allTask     <- TodoRepository.getAll
      allCategory <- CategoryRepository.getAll
    } yield {

      val categoryMap = allCategory.map(ca => (ca.id -> ca)).toMap
      val tasks       = allTask.map(ta     => (ta,categoryMap(ta.v.cid)))

      // viewvalue 生成
      val vv = ViewValueTodo(
        title  = "Todo一覧",
        tasks  = tasks,
        cssSrc = Seq("main.css","todo.css"),
        jsSrc  = Seq("main.js")
      )
      Ok(views.html.todo.List(vv))
    }
  }

  /**
   * TODO 新規作成ページ
   */
  def showAddForm() = Action.async { implicit req =>
    for {
      allCategory <- CategoryRepository.getAll
    } yield {
      // factory view value
      val vv = ViewValueTodoForm(
        title       = "Todo新規作成",
        allCategory = allCategory,
        cssSrc      = Seq("main.css","todo.css"),
        jsSrc       = Seq("main.js")
      )
      Ok(views.html.todo.Add(vv,formData))
    }
  }

  /**
   * TODO 編集ページ
   */
  def showEditForm(id: Long) = Action.async { implicit req =>
    for {
      allCategory <- CategoryRepository.getAll
      task        <- TodoRepository.get(Todo.Id(id))
      category    <- task match {
        case Some(ta) => CategoryRepository.get(ta.v.cid)
        case None     => Future.successful(None)
      }
    } yield {
      task match {
        case None    => BadRequest
        case Some(t) => {
          val defaultData = formData.fill(Todo.FormValue(
            t.id,
            t.v.title,
            t.v.body,
            Some(t.v.status.code.toInt)
          ))
          // viewvalue 生成
          val vv = ViewValueTodoForm(
            title       = "Todo編集",
            allCategory = allCategory,
            cssSrc      = Seq("main.css","todo.css"),
            jsSrc       = Seq("main.js")
          )
          Ok(views.html.todo.Edit(vv,defaultData,t.id))
        }
      }
    }
  }

  /**
   * TODO をINSERT
   */
  def add() = Action.async { implicit req =>
    formData.bindFromRequest.fold(
      errors => Future.successful(BadRequest("failed")),
      data   => {
        val entity = Todo(data.title,data.body,Category.Id(data.cid))
        for {
          _ <- TodoRepository.add(entity)
        } yield {
          // viewvalue 生成
          val vv = ViewValueMessage(
            title       = "Todo新規作成",
            message     = "Todoを新規追加いたしました",
            cssSrc      = Seq("main.css"),
            jsSrc       = Seq("main.js")
          )
          println(Todo.Status.values)
          Ok(views.html.common.Success(vv))
        }
      }
    )
  }

  /**
   * TODO をUPDATE
   */
  def edit(id: Long) = Action.async { implicit req =>
    formData.bindFromRequest.fold(
      errors => Future.successful(BadRequest("failed")),
      data   => {
        for {
          todo   <- TodoRepository.get(Todo.Id(id))
          _      <- todo match {
            case None    => Future.successful(BadRequest("failed"))
            case Some(v) => {
              val entity = v.map(_.copy(
                cid    = Category.Id(data.cid),
                title  = data.title,
                body   = data.body,
                status = Todo.Status(data.status.get.toShort)
              ))
              TodoRepository.update(entity)
            }
          }
        } yield {
          // viewvalue 生成
          val vv = ViewValueMessage(
            title       = "Todo編集",
            message     = "Todoを編集しました",
            cssSrc      = Seq("main.css"),
            jsSrc       = Seq("main.js")
          )
          Ok(views.html.common.Success(vv))
        }
      }
    )
  }

  /**
   * TODO を削除
   */
  def delete(id: Long) = Action.async { implicit req =>
    for {
      todo   <- TodoRepository.remove(Todo.Id(id))
    } yield {
      // factory view value
      val vv = ViewValueMessage(
        title       = "Todo削除",
        message     = "Todoを削除しました",
        cssSrc      = Seq("main.css"),
        jsSrc       = Seq("main.js")
      )
      Ok(views.html.common.Success(vv))
    }
  }
}
