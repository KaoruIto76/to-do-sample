/**
 *
 * to do sample project
 *
 */

package controllers

import scala.concurrent.Future

import javax.inject._
import play.api._
import play.api.mvc._
import play.filters.csrf.CSRFCheck
import play.filters.csrf.CSRFAddToken

import lib.persistence.default._
import lib.model.Category

import model.common.ViewValueMessage
import model.site.category._

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
class CategoryController @Inject()(
  val controllerComponents: ControllerComponents,
  val checkToken:           CSRFCheck
) extends BaseController with I18nSupport {

  implicit val ec = defaultExecutionContext

  // CSRFトークンをチェックするハンドラー
  object CSRFErrorHandler extends play.filters.csrf.CSRF.ErrorHandler {
    def handle(req: RequestHeader, msg: String) =
      Future.successful(Redirect(routes.TodoController.showAllTodo()))
  }

  // formの値をmapping
  val formData = Form(mapping(
    "name"  -> text.verifying("カテゴリ名を入力してください", {!_.isEmpty()}),
    "slug"  -> text.verifying("slugを入力してください", {!_.isEmpty()}),
    "color" -> number,
    )(Category.FormValue.apply)(Category.FormValue.unapply)
  )
  
  /**
   * カテゴリー一覧
   */
  def showAllCategory() = Action.async { implicit req =>
    for {
      categories <- CategoryRepository.getAll
    } yield {

      val vv = ViewValueCategory(
        title      = "カテゴリ一覧",
        categories = categories,
        cssSrc     = Seq("main.css","category.css"),
        jsSrc      = Seq("main.js")
      )      
      Ok(views.html.site.category.List(vv))
    }
  }

  /**
   * カテゴリ編集フォーム
   */
  def showEditForm(id: Long) = Action.async { implicit req =>
    for {
      category  <- CategoryRepository.get(Category.Id(id))
    } yield {
      category match {
        case None     => NotFound
        case Some(ca) => {
          val defaultData = formData.fill(Category.FormValue(
            ca.v.name,
            ca.v.slug,
            ca.v.color.code.toInt
          ))
          // factory view value
          val vv = ViewValueCategoryForm(
            title    = "カテゴリ編集",
            colors   = Category.Color.allColors,
            cssSrc   = Seq("main.css","category.css"),
            jsSrc    = Seq("main.js")
          )
          Ok(views.html.site.category.Edit(vv,defaultData,ca.id))
        }
      }
    }
  }

  /**
   * カテゴリ新規追加フォーム
   */
  def showAddForm() = Action { implicit req =>
    
    val vv = ViewValueCategoryForm(
      title    = "カテゴリ新規作成",
      colors   = Category.Color.allColors,
      cssSrc   = Seq("main.css","category.css"),
      jsSrc    = Seq("main.js")
    )
    Ok(views.html.site.category.Add(vv,formData))
  }

  /**
   * カテゴリデータ更新
   */
  def edit(id: Long) = checkToken(Action.async { implicit req =>
    formData.bindFromRequest.fold(
      errors => Future.successful(BadRequest("failed")),
      data   => {
        for {
          category <- CategoryRepository.get(Category.Id(id))
          _        <- category match {
            case None     => Future.successful(BadRequest)
            case Some(ca) => {
              val entity =  ca.map(_.copy(
                name  = data.name,
                slug  = data.slug,
                color = Category.Color(data.color.toShort)
              ))
              CategoryRepository.update(entity)
            }
          }
        } yield {
          val vv = ViewValueMessage(
            title    = "カテゴリ編集",
            message  = "カテゴリを編集しました",
            cssSrc   = Seq("main.css","category.css"),
            jsSrc    = Seq("main.js")
          )
          Ok(views.html.common.Success(vv))
        }
      }
    )
  }, CSRFErrorHandler)

  /**
   * カテゴリ新規追加
   */
  def add() = checkToken(Action.async { implicit req =>
    formData.bindFromRequest.fold(
      errors => Future.successful(BadRequest("failed")),
      data   => {
        val entity = Category(data.name,data.slug,Category.Color(data.color.toShort))
        for {
          _ <- CategoryRepository.add(entity)
        } yield {

          val vv = ViewValueMessage(
            title       = "カテゴリ新規作成",
            message     = "カテゴリを新規追加しました",
            cssSrc      = Seq("main.css"),
            jsSrc       = Seq("main.js")
          )
          Ok(views.html.common.Success(vv))
        }
      }
    )
  },CSRFErrorHandler)

  /**
   * カテゴリ削除
   */
  def delete(id: Long) = checkToken(Action.async { implicit req =>
    for {
      _  <- CategoryRepository.remove(Category.Id(id))
      _  <- TodoRepository.removeAll
    } yield {
      
      val vv = ViewValueMessage(
        title       = "カテゴリ削除",
        message     = "カテゴリとそれに紐づくTODOを削除しました",
        cssSrc      = Seq("main.css"),
        jsSrc       = Seq("main.js")
      )
      Ok(views.html.common.Success(vv))
    }
  }, CSRFErrorHandler)
}
