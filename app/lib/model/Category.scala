/**
 *
 * to do sample project
 *
 */

package lib.model

import ixias.model._
import ixias.util.EnumStatus
import java.time.LocalDateTime


import Category._
// to_do_categoryデータmapping用model
case class Category(
  id:        Option[Id],          // 管理用ID
  name:      String,              // カテゴリ名
  slug:      String,              // カテゴリの識別子
  color:     Color,               // カテゴリを判別する色
  updatedAt: LocalDateTime = NOW, // データ更新日
  createdAt: LocalDateTime = NOW  // データ作成日
) extends EntityModel[Id]

// コンパニオンオブジェクト
object Category {
  val  Id  = the[Identity[Id]]
  type Id  = Long @@ Category

  type WithNoId   = Entity.WithNoId   [Id, Category]
  type EmbeddedId = Entity.EmbeddedId [Id, Category]

  // INSERT時のIDがAutoincrementのため,IDなしであることを示すオブジェクトに変換
  def apply(name: String, slug:  String, color: Color): WithNoId = {
    Entity.WithNoId (
      new Category(
        None,
        name,
        slug,
        color
      )
    )
  }

  // カテゴリの識別カラーを列挙
  sealed abstract class Color(val code: Short, val color: String) extends EnumStatus
  object Color extends EnumStatus.Of[Color] {
    case object COLOR_BLUE   extends Color(code = 1, color = "#1e90ff")
    case object COLOR_GREEN  extends Color(code = 2, color = "#98fb98")
    case object COLOR_YELLOW extends Color(code = 3, color = "#ffff00")
    case object COLOR_ORANGE extends Color(code = 4, color = "#ff8c00")
    case object COLOR_RED    extends Color(code = 5, color = "#ff0000")
    case object COLOR_GRAY   extends Color(code = 6, color = "#696969")
    case object COLOR_WHITE  extends Color(code = 7, color = "grey")

    // 全カテゴリカラーを取得
    def allColors:Seq[Category.Color] = this.values
  }

  // フォームのデータをbindするためのmodel
  case class FormValue(
    name:  String,
    slug:  String,
    color: Int
  )
}
