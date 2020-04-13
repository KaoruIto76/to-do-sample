/**
 *
 * to do sample project
 *
 */

package controllers

import javax.inject._
import play.api._
import play.api.mvc._

import lib.model.Category
import lib.persistence.default._

import model.site.ViewValueHome

class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  
  // Home ページ
  def index() = Action { implicit req =>
    
    val vv = ViewValueHome(
      title     = "home",
      cssSrc    = Seq("main.css"),
      jsSrc     = Seq("main.js")
    )
    Ok(views.html.site.Home(vv))
  }
}
