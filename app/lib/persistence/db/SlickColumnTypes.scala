/**
 *
 * to do sample project
 *
 */

package lib.persistence.db

import slick.jdbc.JdbcProfile
import lib.model.Todo
import lib.model.Category

trait SlickColumnTypes[P <: JdbcProfile] {

  implicit val driver: P
  import driver.api._

  // -- [ declare original type ] ----------------------------------------------
  implicit  val Id01 = MappedColumnType.base[Todo.Id,     Long](id => id, Todo.Id(_))
  implicit  val Id02 = MappedColumnType.base[Category.Id, Long](id => id, Category.Id(_))
}
