/**
 *
 * to do sample project
 *
 */
package model.site.todo

import lib.model.Todo
import lib.model.Category
import model.common.ViewValueCommon
import model.common.component._

// Todo一覧ページのviewvalue
case class ViewValueTodoList(
  title:   String,
  todoSeq: Seq[(ViewValueTodo,ViewValueCategory)],
  cssSrc:  Seq[String],
  jsSrc:   Seq[String]
) extends ViewValueCommon
