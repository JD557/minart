package eu.joaocosta.minart.graphics.image.kitty

private[kitty] final case class Header(
    width: Int,
    height: Int,
    colorRange: Int,
    action: String,
    medium: String
)

object Header {
  def fromKeyMap(map: Map[String, String]): Header = Header(
    width = map.get("s").flatMap(_.toIntOption).getOrElse(0),
    height = map.get("v").flatMap(_.toIntOption).getOrElse(0),
    colorRange = map.get("f").flatMap(_.toIntOption).getOrElse(32),
    action = map.get("a").getOrElse("t"),
    medium = map.get("t").getOrElse("d")
  )
}
