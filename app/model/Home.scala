/**
 *
 * to do sample project
 *
 */

package model

case class ViewValueHome(
  title:      String,
  categories: Seq[lib.model.Category.EmbeddedId],
  tasks:      Seq[lib.model.Todo.EmbeddedId],
  cssSrc:     Seq[String],
  jsSrc:      Seq[String],
) extends ViewValueCommon
