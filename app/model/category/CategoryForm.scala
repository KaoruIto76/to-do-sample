/**
 *
 * to do sample project
 *
 */
package model.category

import lib.model.Category
import model.ViewValueCommon

// category編集、新規一覧用viewvalue
case class ViewValueCategoryForm(
  title:   String,
  colors:  Seq[Category.Color],
  cssSrc:  Seq[String],
  jsSrc:   Seq[String]
) extends ViewValueCommon

