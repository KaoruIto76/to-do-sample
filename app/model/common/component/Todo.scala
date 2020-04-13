/**
 *
 * to do sample project
 *
 */

package model.common.component

import lib.model.Todo
import lib.model.Category

// viewvalue用のtodoのmodel
case class ViewValueTodo(
  id:         Todo.Id,       // 管理用ID
  cid:        Category.Id,   // カテゴリID
  title:      String,        // TODOのタイトル
  body:       String,        // TODOの内容
  status:     Todo.Status,   // TODOのステータス
)

object ViewValueTodo {

  def create(entity: Todo.EmbeddedId): ViewValueTodo = {
    ViewValueTodo(
      id     = entity.id,
      cid    = entity.v.cid,
      title  = entity.v.title,
      body   = entity.v.body,
      status = entity.v.status
    )
  }
}
