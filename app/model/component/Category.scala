/**
 *
 * to do sample project
 *
 */

import lib.model.Todo
import lib.model.Category

// viewvalue用のtodoのmodel
case class ViewValueCategory(
  id:        Category.Id,    // 管理用ID
  name:      String,         // カテゴリ名
  slug:      String,         // カテゴリの識別子
  color:     Category.Color, // カテゴリを判別する色
)

object ViewValueCategory {

}
