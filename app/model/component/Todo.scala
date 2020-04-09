/**
 *
 * to do sample project
 *
 */

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
