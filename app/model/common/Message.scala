/**
 *
 * to do sample project
 *
 */
package model.common

import model.common.ViewValueCommon

// edit,add,delete, 後のメッセージページのviewvalue
case class ViewValueMessage(
  title:   String,
  message: String,
  cssSrc:  Seq[String],
  jsSrc:   Seq[String]
) extends ViewValueCommon

