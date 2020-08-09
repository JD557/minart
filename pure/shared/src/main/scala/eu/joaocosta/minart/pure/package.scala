package eu.joaocosta.minart

import eu.joaocosta.minart.core.Canvas

package object pure {
  type CanvasIO[+A] = RIO[Canvas, A]
}

