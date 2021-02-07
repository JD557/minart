package eu.joaocosta.minart.core

import eu.joaocosta.minart.backend.defaults.DefaultBackend

/** A low-level version of a canvas that provides its own canvas manager.
  */
trait LowLevelCanvas extends Canvas with CanvasManager

object LowLevelCanvas {

  /** Returns [[LowLevelCanvas]] for the default backend for the target platform.
    *
    * @param settings Settings used to create the new canvas
    * @return [[LowLevelCanvas]] using the default backend for the target platform
    */
  def default(settings: Canvas.Settings)(implicit d: DefaultBackend[Canvas.Settings, LowLevelCanvas]): LowLevelCanvas =
    d.defaultValue(settings)
}
