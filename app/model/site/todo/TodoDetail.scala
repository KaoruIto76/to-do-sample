/**
 *
 * to do sample project
 *
 */
package model.site.todo

import lib.model.Todo
import lib.model.Category
import model.common.ViewValueCommon

// Todo詳細ページのviewvlaue
case class ViewValueTodoDetail(
  title:    String,
  task:     Todo.EmbeddedId,
  category: Category.EmbeddedId,
  cssSrc:   Seq[String],
  jsSrc:    Seq[String]
) extends ViewValueCommon
