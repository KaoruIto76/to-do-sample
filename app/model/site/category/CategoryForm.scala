/**
 *
 * to do sample project
 *
 */
package model.site.category

import lib.model.Category
import model.common.ViewValueCommon

// category編集、新規一覧用viewvalue
case class ViewValueCategoryForm(
  title:   String,
  colors:  Seq[Category.Color],
  cssSrc:  Seq[String],
  jsSrc:   Seq[String]
) extends ViewValueCommon

