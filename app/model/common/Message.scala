/**
 *
 * to do sample project
 *
 */
package model.common

// edit,add,delete, 後のメッセージページのviewvalue
case class ViewValueMessage(
  title:   String,
  message: String,
  cssSrc:  Seq[String],
  jsSrc:   Seq[String]
) extends ViewValueCommon

