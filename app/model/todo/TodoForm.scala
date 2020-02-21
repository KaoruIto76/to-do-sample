/**
 *
 * to do sample project
 *
 */
package model.todo

import lib.model.Category
import lib.model.Todo
import model.ViewValueCommon

// Todoの編集、新規追加ページのviewvalue
case class ViewValueTodoForm(
  title:       String,
  allCategory: Seq[Category.EmbeddedId],
  cssSrc:      Seq[String],
  jsSrc:       Seq[String]
) extends ViewValueCommon
