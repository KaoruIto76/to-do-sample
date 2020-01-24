/**
 *
 * to do sample project
 *
 */
package model.category

import lib.model.Category
import lib.model.Todo
import model.ViewValueCommon

case class ViewValueCategoryDetail(
  title:      String,
  category:   Category.EmbeddedId,
  tasks:      Seq[Todo.EmbeddedId],
  cssSrc:     Seq[String],
  jsSrc:      Seq[String]
) extends ViewValueCommon

