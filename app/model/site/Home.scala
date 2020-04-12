/**
 *
 * to do sample project
 *
 */

package model.site

import model.common.ViewValueCommon

// Topページのviewvalue
case class ViewValueHome(
  title:  String,
  cssSrc: Seq[String],
  jsSrc:  Seq[String],
) extends ViewValueCommon

