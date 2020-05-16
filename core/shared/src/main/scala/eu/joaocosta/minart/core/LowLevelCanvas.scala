package eu.joaocosta.minart.core

import eu.joaocosta.minart.backend.defaults.DefaultBackend

/**
 * A low-level version of a canvas that provides its own canvas manager
 */
trait LowLevelCanvas extends Canvas with CanvasManager

object LowLevelCanvas {
  def default(settings: Canvas.Settings)(implicit d: DefaultBackend[Canvas.Settings, LowLevelCanvas]): LowLevelCanvas =
    d.defaultValue(settings)
}
