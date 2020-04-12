/**
 *
 * to do sample project
 *
 */
package model.site.todo

import lib.model.Todo
import lib.model.Category
import model.common.ViewValueCommon

// Todo一覧ページのviewvalue
case class ViewValueTodo(
  title:  String,
  tasks:  Seq[(Todo.EmbeddedId,Category.EmbeddedId)],
  cssSrc: Seq[String],
  jsSrc:  Seq[String]
) extends ViewValueCommon
