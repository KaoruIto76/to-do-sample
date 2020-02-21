/**
 *
 * to do sample project
 *
 */
package model.category

import lib.model.Category
import model.ViewValueCommon

// category一覧ページのviewvalue
case class ViewValueCategory(
  title:      String,
  categories: Seq[Category.EmbeddedId],
  cssSrc:     Seq[String],
  jsSrc:      Seq[String]
) extends ViewValueCommon
