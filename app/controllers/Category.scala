/**
 *
 * to do sample project
 *
 */

package controllers

import javax.inject._
import play.api._
import play.api.mvc._

import lib.persistence.default._
import lib.model.Category

import model.category._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class CategoryController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  implicit val ec = scala.concurrent.ExecutionContext.global
  
  /**
   * show all category
   */
  def show() = Action.async { implicit req =>
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
      Ok(views.html.category.Category(vv))
    }
  }

  /**
   * show category detail
   */
  def detail(id: Long) = Action.async { implicit req =>
    for {
      // Some(category)  <- CategoryRepository.get(Category.Id(id)) <- Bad
      category  <- CategoryRepository.get(Category.Id(id))
    } yield {

      category match {
        case None    => NotFound
        case Some(ca) => {
          // factory view value
          val vv = ViewValueCategoryDetail(
            title    = "カテゴリ一覧",
            category = ca,
            tasks    = Seq.empty,
            cssSrc   = Seq("main.css","category.css"),
            jsSrc    = Seq("main.js")
          )   
          Ok(s"$category")
        }
      }
    }
  }
}
