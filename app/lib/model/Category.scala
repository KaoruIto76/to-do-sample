/**
 *
 * to do sample project
 *
 */

package lib.model

import ixias.model._
import java.time.LocalDateTime


import Category._
// model of category
case class Category(
  id:         Option[Id],
  name:       String,
  slug:       String,
  updatedAt:  LocalDateTime = NOW,
  createdAt:  LocalDateTime = NOW
) extends EntityModel[Id]

// companion object
object Category {
  val  Id  = the[Identity[Id]]
  type Id  = Long @@ Category

  type WithNoId   = Entity.WithNoId   [Id, Category]
  type EmbeddedId = Entity.EmbeddedId [Id, Category]

  def apply(name: String, slug:  String): WithNoId = {
    Entity.WithNoId (
      new Category(
        None,
        name,
        slug
      )
    )
  }
}
