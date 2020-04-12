/**
 *
 * to do sample project
 *
 */
package model.site.category

import lib.model.Category
import lib.model.Todo
import model.common.ViewValueCommon

// category詳細ページのviewvalue
case class ViewValueCategoryDetail(
  title:      String,
  category:   Category.EmbeddedId,
  tasks:      Seq[Todo.EmbeddedId],
  cssSrc:     Seq[String],
  jsSrc:      Seq[String]
) extends ViewValueCommon

