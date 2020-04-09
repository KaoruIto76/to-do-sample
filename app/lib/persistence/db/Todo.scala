/**
 *
 * to do sample project
 *
 */

package lib.persistence.db

import java.time.LocalDateTime
import slick.jdbc.JdbcProfile
import ixias.persistence.model.Table

import lib.model.Todo
import lib.model.Category

case class TodoTable[P <: JdbcProfile]()(implicit val driver: P)
  extends Table[Todo, P] {
  
  import api._

  // --[ テーブル定義 ] ----------------------------------------------
  lazy val dsn = Map(
    "master" -> DataSourceName("ixias.db.mysql://master/to_do_sample"),
    "slave"  -> DataSourceName("ixias.db.mysql://slave/to_do_sample")
  )

  // --[ クエリー定義 ] ----------------------------------------------
  class Query extends BasicQuery(new Table(_)) {}
  lazy val query = new Query

  // --[ テーブルのカラム定義 ] --------------------------------------
  class Table(tag: Tag) extends BasicTable(tag, "to_do") {
    // Columns
    /* @1 */ def id        = column[Todo.Id]       ("id",          O.UInt64, O.PrimaryKey, O.AutoInc)
    /* @2 */ def cid       = column[Category.Id]   ("category_id", O.UInt64)
    /* @3 */ def title     = column[String]        ("title",       O.Utf8Char255)
    /* @4 */ def body      = column[String]        ("body",        O.Utf8Char255)
    /* @5 */ def status    = column[Todo.Status]   ("status",      O.UInt8)
    /* @6 */ def updatedAt = column[LocalDateTime] ("updated_at",  O.TsCurrent)
    /* @7 */ def createdAt = column[LocalDateTime] ("created_at",  O.Ts)

    // カラムをtupleで表現
    type TableElementTuple = (
      Option[Todo.Id], Category.Id, String, String, Todo.Status, LocalDateTime, LocalDateTime
    )
  
    // DB <=> Scala の相互のmapping定義
    def * = (id.?, cid, title, body, status, updatedAt, createdAt) <> (
      // Tuple(table) => Model
      (t: TableElementTuple) => Todo(
        t._1, t._2, t._3, t._4, t._5, t._6, t._7
      ),
      // Model => Tuple(table)
      (v: TableElementType) => Todo.unapply(v).map { t => (
        t._1, t._2, t._3, t._4, t._5, LocalDateTime.now(), t._7
      )}
    )
  }
}
