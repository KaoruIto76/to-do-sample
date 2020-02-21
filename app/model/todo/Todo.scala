/**
 *
 * to do sample project
 *
 */
package model.todo

import lib.model.Todo
import lib.model.Category
import model.ViewValueCommon

// Todo一覧ページのviewvalue
case class ViewValueTodo(
  title:  String,
  tasks:  Seq[(Todo.EmbeddedId,Category.EmbeddedId)],
  cssSrc: Seq[String],
  jsSrc:  Seq[String]
) extends ViewValueCommon
