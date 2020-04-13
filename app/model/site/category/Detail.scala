/**
 *
 * to do sample project
 *
 */
package model.site.category

import lib.model.Category
import lib.model.Todo
import model.common.ViewValueCommon
import model.common.component._

// category詳細ページのviewvalue
case class ViewValueCategoryDetail(
  title:      String,
  category:   ViewValueCategory,
  tasks:      Seq[ViewValueTodo],
  cssSrc:     Seq[String],
  jsSrc:      Seq[String]
) extends ViewValueCommon

