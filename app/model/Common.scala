/**
 *
 * to do sample project
 *
 */
package model

// 共通のviewValue
trait ViewValueCommon {
  val title:  String      // pageのタイトル
  val cssSrc: Seq[String] // pageで読み込むcssのファイル名
  val jsSrc:  Seq[String] // pageで読み込むjavascriptのファイル名
}

