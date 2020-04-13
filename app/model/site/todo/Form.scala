/**
 *
 * to do sample project
 *
 */
package model.site.todo

import lib.model.Category
import lib.model.Todo
import model.common.ViewValueCommon
import model.common.component._

// Todoの編集、新規追加ページのviewvalue
case class ViewValueTodoForm(
  title:       String,
  allCategory: Seq[ViewValueCategory],
  cssSrc:      Seq[String],
  jsSrc:       Seq[String]
) extends ViewValueCommon
