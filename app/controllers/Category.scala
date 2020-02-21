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

import lib.persistence.default._
import lib.model.Category

import model.ViewValueMessage
import model.category._

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
class CategoryController @Inject()(val controllerComponents: ControllerComponents) extends BaseController with I18nSupport {

  implicit val ec = scala.concurrent.ExecutionContext.global

  // form value
  val formData = Form(mapping(
    "name"  -> text.verifying("カテゴリ名を入力してください", {!_.isEmpty()}),
    "slug"  -> text.verifying("slugを入力してください", {!_.isEmpty()}),
    "color" -> number,
    )(Category.FormValue.apply)(Category.FormValue.unapply)
  )
  
  /**
   * show all category
   */
  def showAllCategory() = Action.async { implicit req =>
    for {
      categories <- CategoryRepository.getAll
    } yield {

      // factory view value
      val vv = ViewValueCategory(
        title      = "カテゴリ一覧",
        categories = categories,
        cssSrc     = Seq("main.css","category.css"),
        jsSrc      = Seq("main.js")
      )      
      Ok(views.html.category.Main(vv))
    }
  }

  /**
   * show category detail
   */
  def showEditForm(id: Long) = Action.async { implicit req =>
    for {
      // Some(category)  <- CategoryRepository.get(Category.Id(id)) <- Bad
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
          Ok(views.html.category.Edit(vv,defaultData,ca.id))
        }
      }
    }
  }

  /**
   * show category detail
   */
  def showAddForm() = Action { implicit req =>
    // factory view value
    val vv = ViewValueCategoryForm(
      title    = "カテゴリ新規作成",
      colors   = Category.Color.allColors,
      cssSrc   = Seq("main.css","category.css"),
      jsSrc    = Seq("main.js")
    )
    Ok(views.html.category.Add(vv,formData))
  }

  /**
   * show category detail
   */
  def edit(id: Long) = Action.async { implicit req =>
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
          // factory view value
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
  }

  /**
   * show category detail
   */
  def add() = Action.async { implicit req =>
    formData.bindFromRequest.fold(
      errors => Future.successful(BadRequest("failed")),
      data   => {
        val entity = Category(data.name,data.slug,Category.Color(data.color.toShort))
        for {
          _ <- CategoryRepository.add(entity)
        } yield {
          // factory view value
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
  }

  /**
   * delete category
   */
  def delete(id: Long) = Action.async { implicit req =>
    for {
      todo   <- CategoryRepository.remove(Category.Id(id))
    } yield {
      // factory view value
      val vv = ViewValueMessage(
        title       = "カテゴリ削除",
        message     = "カテゴリを削除しました",
        cssSrc      = Seq("main.css"),
        jsSrc       = Seq("main.js")
      )
      Ok(views.html.common.Success(vv))
    }
  }
}
