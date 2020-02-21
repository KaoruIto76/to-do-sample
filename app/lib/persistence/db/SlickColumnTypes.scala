/**
 *
 * to do sample project
 *
 */

package lib.persistence.db

import slick.jdbc.JdbcProfile
import lib.model.Todo
import lib.model.Category
import lib.model.Category.Color

trait SlickColumnTypes[P <: JdbcProfile] {

  implicit val driver: P
  import driver.api._

  // -- [ 独自で定義した型を暗黙的にプリミティブ型に変換 ] -----------------------
  implicit  val Id01 = MappedColumnType.base[Todo.Id,      Long](id => id,     Todo.Id(_))
  implicit  val Id02 = MappedColumnType.base[Category.Id,  Long](id => id,     Category.Id(_))
  implicit  val enum01 = MappedColumnType.base[Color,      Short](v => v.code, Color(_))
}
