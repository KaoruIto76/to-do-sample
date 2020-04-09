/**
 *
 * to do sample project
 *
 */

package lib.persistence

import scala.concurrent.Future
import slick.jdbc.JdbcProfile
import ixias.persistence.SlickRepository

import lib.model.Todo
import lib.model.Category

case class TodoRepository[P <: JdbcProfile]()(implicit val driver: P)
    extends SlickRepository[Todo.Id, Todo, P]
    with db.SlickResourceProvider[P]
{
  import api._

  def get(id:Id):Future[Option[EntityEmbeddedId]] = {
     RunDBAction(TodoTable, "slave") { _
       .filter(_.id === id)
       .result.headOption
     }
  }

  def getAll: Future[Seq[EntityEmbeddedId]] = {
    RunDBAction(TodoTable, "slave") { _
      .result
    }
  }

  def filterByCategoryId(cid: Category.Id): Future[Seq[EntityEmbeddedId]] = {
    RunDBAction(TodoTable, "slave") { _
      .filter(_.cid === cid)
      .result
    }
  }

  def add(data:EntityWithNoId):Future[Id] = {
    RunDBAction(TodoTable) { slick =>
      slick returning slick.map(_.id) += data.v
    }
  }

  def remove(id:Id):Future[Option[EntityEmbeddedId]] = {
    RunDBAction(TodoTable) { slick =>
      val row = slick.filter(_.id === id)
      for {
        old <- row.result.headOption
        _   <- row.delete
      } yield old
    }
  }

  def update(data: EntityEmbeddedId): Future[Option[EntityEmbeddedId]] = {
    RunDBAction(TodoTable) { slick =>
      val row = slick.filter(_.id === data.id)
      for {
        old <- row.result.headOption
        _   <- old match {
          case None    => DBIO.successful(0)
          case Some(_) => row.update(data.v)
        }
      } yield old
    }
  }
}
