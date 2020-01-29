/**
 *
 * to do sample project
 *
 */
package model.todo

import lib.model.Category
import model.ViewValueCommon

case class ViewValueTodoAdd(
  title:       String,
  allCategory: Seq[Category.EmbeddedId],
  cssSrc:      Seq[String],
  jsSrc:       Seq[String]
) extends ViewValueCommon
