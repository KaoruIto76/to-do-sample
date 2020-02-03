/**
 *
 * to do sample project
 *
 */
package model

case class ViewValueMessage(
  title:   String,
  message: String,
  cssSrc:  Seq[String],
  jsSrc:   Seq[String]
) extends ViewValueCommon
