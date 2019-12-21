/**
 *
 * to do sample project
 *
 */

package model

trait ViewValueBase {
  val title:     String
  val assetsCss: Seq[String] = Seq.empty
  val assetsJs:  Seq[String] = Seq.empty

  def getAssetsCss: Seq[String] = {
    this.assetsCss.map("stylesheets/" + _)
  }

  def getAssetsJs: Seq[String] = {
    this.assetsJs.map("javascripts/" + _)
  }
}
