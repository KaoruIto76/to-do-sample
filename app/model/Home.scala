/**
 *
 * to do sample project
 *
 */
package model

case class ViewValueHome(
  test:   String
) extends ViewValueBase {

  override val title:     String      = "home"
  override val assetsJs:  Seq[String] = Seq("main.js")
  override val assetsCss: Seq[String] = Seq("main.css")
}
