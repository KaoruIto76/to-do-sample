/**
 *
 * to do sample project
 *
 */

package model.common.component

import lib.model.Todo
import lib.model.Category

// viewvalue用のtodoのmodel
case class ViewValueCategory(
  id:    Category.Id,    // 管理用ID
  name:  String,         // カテゴリ名
  slug:  String,         // カテゴリの識別子
  color: Category.Color, // カテゴリを判別する色
)

object ViewValueCategory {

  def create(entity: Category.EmbeddedId): ViewValueCategory = {
    ViewValueCategory(
      id    = entity.id,
      name  = entity.v.name,
      slug  = entity.v.slug,
      color = entity.v.color
    )
  }
}
