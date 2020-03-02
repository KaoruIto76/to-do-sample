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
  status:     Status        = Status.IS_TODO,
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

  // ステータス
  sealed abstract class Status(val code: Short, val value: String) extends EnumStatus
  object Status extends EnumStatus.Of[Status] {
    case object IS_TODO             extends Status(code = 0, value = "TODO")
    case object IS_PROGRES          extends Status(code = 1, value = "実装中")
    case object IS_IN_REVIRE        extends Status(code = 2, value = "レビュー")
    case object IS_WAIT_FOR_RELEASE extends Status(code = 3, value = "リリース待ち")
  }

  // フォームのデータをbindするためのmodel
  case class FormValue(
    cid:    Long,
    title:  String,
    body:   String,
    status: Option[Int]
  )
}
