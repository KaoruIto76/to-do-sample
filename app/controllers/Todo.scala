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

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class TodoController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  implicit val ec = scala.concurrent.ExecutionContext.global
  
  /**
   * show all todo
   */
  def show() = Action.async { implicit req =>
    for {
      allTask     <- TodoRepository.getAll
      allCategory <- CategoryRepository.getAll
    } yield {

      val categoryMap = allCategory.map(x => (x.id -> x)).toMap
      val tasks       = allTask.map(x => (x,categoryMap(x.v.cid)))

      // factory view value
      val vv = ViewValueTodo(
        title  = "Todo一覧",
        tasks  = tasks,
        cssSrc = Seq("main.css","todo.css"),
        jsSrc  = Seq("main.js")
      )      
      Ok(views.html.todo.Todo(vv))
    }
  }

  /**
   * show todo detail
   */
  def detail(id: Long) = Action.async { implicit req =>
    val test = for {
      task <- TodoRepository.get(Todo.Id(id))
    } yield task
    Future.successful(Ok("aaa"))
  }
//            category match {
//              case None     => NotFound
//              case Some(ca) => {
//                // factory view value
//                val vv = ViewValueTodoDetail(
//                  title    = "Todo一覧",
//                  task     = x,
//                  category = ca,
//                  cssSrc   = Seq("main.css","todo.css"),
//                  jsSrc    = Seq("main.js")
//                )
//                Ok(s"$vv")
//              }
//            }
//          }
//        }
}

