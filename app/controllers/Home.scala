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

import model.site.ViewValueHome

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  
  def index() = Action { implicit req =>
    // factory view value
    val vv = ViewValueHome(
      title     = "home",
      cssSrc    = Seq("main.css"),
      jsSrc     = Seq("main.js")
    )
    Ok(views.html.site.Home(vv))
  }
}
