package controllers

import javax.inject._
import play.api._
import play.api.mvc._

import lib.persistence.default._
import lib.model.Category

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
    val id = Category.Id(1)
    for {
      res <- CategoryRepository.get(id)
    } yield Ok(s"$res")
  }
}
