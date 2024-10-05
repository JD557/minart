package eu.joaocosta.minart.geometry

/** Represents lines, curves or countours that can be stroked.
  */
enum Stroke {
  case Line(p1: Point, p2: Point)
  case Circle(center: Point, radius: Double)
}
