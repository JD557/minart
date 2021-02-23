package eu.joaocosta.minart.core

import eu.joaocosta.minart.backend.defaults.DefaultBackend

/** A low-level version of a canvas that provides its own canvas manager.
  */
trait LowLevelCanvas extends Canvas with CanvasManager {
  def settings: Option[Canvas.Settings] = Option(currentSettings).map(_.settings)
}

object LowLevelCanvas {

  /** Returns [[LowLevelCanvas]] for the default backend for the target platform.
    *
    * @return [[LowLevelCanvas]] using the default backend for the target platform
    */
  def default()(implicit d: DefaultBackend[Any, LowLevelCanvas]): LowLevelCanvas =
    d.defaultValue()
}
