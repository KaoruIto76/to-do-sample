/**
 *
 * to do sample project
 *
 */

package lib.model

import ixias.model._
import ixias.util.EnumStatus
import java.time.LocalDateTime

import Todo._

// to_do_categoryデータmapping用model
case class Todo(
  id:         Option[Id],
  cid:        Category.Id,
  title:      String,
  body:       String,
  status:     Status,
  updatedAt:  LocalDateTime = NOW,
  createdAt:  LocalDateTime = NOW
) extends EntityModel[Id]

// コンパニオンオブジェクト
object Todo {
  val  Id  = the[Identity[Id]]
  type Id  = Long @@ Todo

  type WithNoId   = Entity.WithNoId   [Id, Todo]
  type EmbeddedId = Entity.EmbeddedId [Id, Todo]

  // INSERT時のIDがAutoincrementのため,IDなしであることを示すオブジェクトに変換
  def apply(title: String, body:  String, cid: Category.Id, status: Status): WithNoId = {
    Entity.WithNoId (
      new Todo(
        None,
        cid,
        title,
        body,
        status
      )
    )
  }

  // ステータス
  sealed abstract class Status(val code: Short, val value: String) extends EnumStatus
  object Status extends EnumStatus.Of[Status] {
    case object IS_INACTIVE extends Status(code = 0,   value = "無効")
    case object IS_ACTIVE   extends Status(code = 100, value = "有効")
  }

  // フォームのデータをbindするためのmodel
  case class FormValue(
    cid:    Long,
    title:  String,
    body:   String
  )
}
