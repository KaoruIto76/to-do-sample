/**
 *
 * to do sample project
 *
 */

package lib.model

import ixias.model._
import java.time.LocalDateTime

import Todo._
// model of todo
case class Todo(
  id:         Option[Id],
  cid:        Category.Id,
  title:      String,
  body:       String,
  updatedAt:  LocalDateTime = NOW,
  createdAt:  LocalDateTime = NOW
) extends EntityModel[Id]

// companion object
object Todo {
  val  Id  = the[Identity[Id]]
  type Id  = Long @@ Todo

  type WithNoId   = Entity.WithNoId   [Id, Todo]
  type EmbeddedId = Entity.EmbeddedId [Id, Todo]

  def apply(title: String, body:  String, cid: Category.Id): WithNoId = {
    Entity.WithNoId (
      new Todo(
        None,
        cid,
        title,
        body
      )
    )
  }

  // mapping to form data
  case class FormValue(
    cid:    Long,
    title:  String,
    body:   String
  )
}
