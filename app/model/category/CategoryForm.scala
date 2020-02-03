/**
 *
 * to do sample project
 *
 */
package model.category

import lib.model.Category
import model.ViewValueCommon

case class ViewValueCategoryForm(
  title:   String,
  colors:  Seq[Category.CategoryColor],
  cssSrc:  Seq[String],
  jsSrc:   Seq[String]
) extends ViewValueCommon

