/**
 *
 * to do sample project
 *
 */
package model.site.category

import lib.model.Category
import model.common.ViewValueCommon
import model.common.component.ViewValueCategory

// category一覧ページのviewvalue
case class ViewValueCategoryList(
  title:      String,
  categories: Seq[ViewValueCategory],
  cssSrc:     Seq[String],
  jsSrc:      Seq[String]
) extends ViewValueCommon
