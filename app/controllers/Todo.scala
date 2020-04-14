/**
 *
 * to do sample project
 *
 */

package controllers

import javax.inject._
import scala.concurrent.Future

import play.filters.csrf.CSRFCheck
import play.filters.csrf.CSRFAddToken
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Valid
import play.api.data.validation.Constraint
import play.api.data.validation.Invalid
import play.api.i18n.I18nSupport

import lib.model.Category
import lib.model.Todo
import lib.persistence.default._

import model.common.ViewValueMessage
import model.common.component._
import model.site.todo._

class TodoController @Inject()(
  val controllerComponents: ControllerComponents,
  val checkToken:           CSRFCheck
) extends BaseController with I18nSupport {

  implicit val ec = defaultExecutionContext

  // CSRFトークンをチェックするハンドラー
  object CSRFErrorHandler extends play.filters.csrf.CSRF.ErrorHandler {
    def handle(req: RequestHeader, msg: String) =
      Future.successful(Redirect(routes.TodoController.showAllTodo()))
  }

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
      allTodos    <- TodoRepository.getAll
      allCategory <- CategoryRepository.getAll
    } yield {

      val categoryMap        = allCategory.map(ca => (ca.id -> ca)).toMap
      val vvTodoWithCategory = allTodos.map(todo  => {
        (ViewValueTodo.create(todo),ViewValueCategory.create(categoryMap(todo.v.cid)))
      })

      // viewvalue 生成
      val vv = ViewValueTodoList(
        title  = "Todo一覧",
        todos  = vvTodoWithCategory,
        cssSrc = Seq("main.css","todo.css"),
        jsSrc  = Seq("main.js")
      )
      Ok(views.html.site.todo.List(vv))
    }
  }

  /**
   * 指定されたカテゴリにひもづくTODO 一覧
   */
  def showTodoBySlug(slug: String) = Action.async { implicit req =>
    for {
      Some(category) <- CategoryRepository.findBySlug(slug)
      todos          <- TodoRepository.filterByCategoryId(category.id)
    } yield {

      val vvTodoWithCategory = todos.map(todo => {
        (ViewValueTodo.create(todo),ViewValueCategory.create(category))
      })

      // viewvalue 生成
      val vv = ViewValueTodoList(
        title  = category.v.name + "のTodo一覧",
        todos  = vvTodoWithCategory,
        cssSrc = Seq("main.css","todo.css"),
        jsSrc  = Seq("main.js")
      )
      Ok(views.html.site.todo.List(vv))
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
        allCategory = allCategory.map(ViewValueCategory.create(_)),
        cssSrc      = Seq("main.css","todo.css"),
        jsSrc       = Seq("main.js")
      )
      Ok(views.html.site.todo.Add(vv,formData))
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
        case None    => NotFound("そのIdのTodoは存在しません")
        case Some(t) => {
          val defaultFormvalue = formData.fill(Todo.FormValue(
            t.id,
            t.v.title,
            t.v.body,
            Some(t.v.status.code.toInt)
          ))
          // viewvalue 生成
          val vv = ViewValueTodoForm(
            title       = "Todo編集",
            allCategory = allCategory.map(ViewValueCategory.create(_)),
            cssSrc      = Seq("main.css","todo.css"),
            jsSrc       = Seq("main.js")
          )
          Ok(views.html.site.todo.Edit(vv,defaultFormvalue,t.id))
        }
      }
    }
  }

  /**
   * TODO をINSERT
   */
  def add() = checkToken( Action.async { implicit req =>
    formData.bindFromRequest.fold(
      errors => Future.successful(BadRequest("failed")),
      data   => {
        val entity = Todo(None,Category.Id(data.cid),data.title,data.body).toWithNoId
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
          Ok(views.html.common.Success(vv))
        }
      }
    )
  }, CSRFErrorHandler)

  /**
   * TODO をUPDATE
   */
  def edit(id: Long) = checkToken(Action.async { implicit req =>
    formData.bindFromRequest.fold(
      errors => Future.successful(BadRequest("failed")),
      data   => {
        for {
          todo   <- TodoRepository.get(Todo.Id(id))
          _      <- todo match {
            case None    => Future.successful(NotFound("そのIdのTodoは存在しません"))
            case Some(v) => {
              val newEntity = v.map(_.copy(
                cid    = Category.Id(data.cid),
                title  = data.title,
                body   = data.body,
                status = Todo.Status(data.status.get.toShort)
              ))
              TodoRepository.update(newEntity)
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
  }, CSRFErrorHandler)

  /**
   * TODO を削除
   */
  def delete(id: Long) = checkToken(Action.async { implicit req =>
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
  }, CSRFErrorHandler)
}
