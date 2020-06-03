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
  id:         Option[Id],                     // 管理用ID
  cid:        Category.Id,                    // カテゴリID
  title:      String,                         // TODOのタイトル
  body:       String,                         // TODOの内容
  status:     Status        = Status.IS_TODO, // TODOのステータス
  updatedAt:  LocalDateTime = NOW,            // データ更新日
  createdAt:  LocalDateTime = NOW             // データ作成日
) extends EntityModel[Id]

// コンパニオンオブジェクト
object Todo {
  val  Id  = the[Identity[Id]]
  type Id  = Long @@ Todo

  type WithNoId   = Entity.WithNoId   [Id, Todo]
  type EmbeddedId = Entity.EmbeddedId [Id, Todo]

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
