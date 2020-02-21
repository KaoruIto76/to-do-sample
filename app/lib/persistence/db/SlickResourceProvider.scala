/**
 *
 * to do sample project
 *
 */

package lib.persistence.db

import slick.jdbc.JdbcProfile

trait SlickResourceProvider[P <: JdbcProfile] {

  implicit val driver: P

  // --[ テーブル定義 ] --------------------------------------
  object TodoTable     extends TodoTable
  object CategoryTable extends CategoryTable

  lazy val AllTables = Seq(
    TodoTable,
    CategoryTable
  )
}
