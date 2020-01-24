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

import model.ViewValueHome

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  implicit val ec = scala.concurrent.ExecutionContext.global
  
  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action.async { implicit req =>
    for {
      categoris <- CategoryRepository.getAll
      tasks     <- TodoRepository.getAll
    } yield {

      val vv = ViewValueHome(
        "home",
        categoris,
        tasks,
        Seq("main.css"),
        Seq("main.js")
      )

      Ok(views.html.home(vv))
    }
  }
}
