package eu.joaocosta.minart.graphics

import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.backend.subsystem.LowLevelSubsystem

/** A low-level version of a canvas with init and close methods.
  */
trait LowLevelCanvas extends Canvas with LowLevelSubsystem.Extended[Canvas.Settings, LowLevelCanvas.ExtendedSettings] {
  protected lazy val defaultSettings = LowLevelCanvas.ExtendedSettings(Canvas.Settings(0, 0))
  protected def elideSettings(extendedSettings: LowLevelCanvas.ExtendedSettings): Canvas.Settings =
    extendedSettings.settings

  /** The settings applied to this canvas.
    */
  def canvasSettings: Canvas.Settings = settings
}

object LowLevelCanvas {

  /** Returns a new [[LowLevelCanvas]] for the default backend for the target platform.
    *
    * @return [[LowLevelCanvas]] using the default backend for the target platform
    */
  def create()(implicit backend: DefaultBackend[Any, LowLevelCanvas]): LowLevelCanvas =
    backend.defaultValue()

  /** Internal data structure containing canvas settings and precomputed values.
    */
  case class ExtendedSettings(
      settings: Canvas.Settings,
      windowWidth: Int,
      windowHeight: Int
  ) {
    val scaledWidth  = settings.width * settings.scale
    val scaledHeight = settings.height * settings.scale
    val canvasX      = (windowWidth - scaledWidth) / 2
    val canvasY      = (windowHeight - scaledHeight) / 2
  }

  object ExtendedSettings {
    def apply(settings: Canvas.Settings): ExtendedSettings =
      ExtendedSettings(settings, settings.width * settings.scale, settings.height * settings.scale)
  }
}
