/**
 *
 * to do sample project
 *
 */
package model

import lib.model.Category

case class ViewValueCategory(
  categories:   Seq[Category]
) extends ViewValueBase {

  override val title:     String      = "category"
  override val assetsJs:  Seq[String] = Seq("main.js","category.js")
  override val assetsCss: Seq[String] = Seq("main.css","category.css")
}
