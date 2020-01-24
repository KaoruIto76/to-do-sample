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
// model of category
case class Category(
  id:            Option[Id],
  name:          String,
  slug:          String,
  categoryColor: CategoryColor,
  updatedAt:     LocalDateTime = NOW,
  createdAt:     LocalDateTime = NOW
) extends EntityModel[Id]

// companion object
object Category {
  val  Id  = the[Identity[Id]]
  type Id  = Long @@ Category

  type WithNoId   = Entity.WithNoId   [Id, Category]
  type EmbeddedId = Entity.EmbeddedId [Id, Category]

  def apply(name: String, slug:  String, categoryColor: CategoryColor): WithNoId = {
    Entity.WithNoId (
      new Category(
        None,
        name,
        slug,
        categoryColor
      )
    )
  }

  // declare enum for priorityFlag (abstract class)
  sealed abstract class CategoryColor(val code: Short, val color: String) extends EnumStatus
  object CategoryColor extends EnumStatus.Of[CategoryColor] {
    case object COLOR_BLUE   extends CategoryColor(code = 1, color = "#1e90ff")
    case object COLOR_GREEN  extends CategoryColor(code = 2, color = "#98fb98")
    case object COLOR_YELLOW extends CategoryColor(code = 3, color = "#ffff00")
    case object COLOR_ORANGE extends CategoryColor(code = 4, color = "#ff8c00")
    case object COLOR_RED    extends CategoryColor(code = 5, color = "#ff0000")
    case object COLOR_GRAY   extends CategoryColor(code = 6, color = "#696969")
    case object COLOR_WHITE  extends CategoryColor(code = 7, color = "#ffffff")
  }
}
