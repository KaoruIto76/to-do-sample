/**
 *
 * to do sample project
 *
 */

package lib.persistence.db

import slick.jdbc.JdbcProfile

trait SlickResourceProvider[P <: JdbcProfile] {

  implicit val driver: P

  // --[ declare table ] ------------------------------------------------
  object TodoTable     extends TodoTable
  object CategoryTable extends CategoryTable

  // --[ declare all table ] --------------------------------------------
  lazy val AllTables = Seq(
    TodoTable,
    CategoryTable
  )
}
