package eu.joaocosta.minart.graphics

import eu.joaocosta.minart.backend.defaults.*
import eu.joaocosta.minart.backend.subsystem.LowLevelSubsystem

/** A low-level version of a canvas with init and close methods.
  */
trait LowLevelCanvas extends Canvas with LowLevelSubsystem.Extended[Canvas.Settings, LowLevelCanvas.ExtendedSettings] {
  protected lazy val defaultSettings = LowLevelCanvas.ExtendedSettings(Canvas.Settings(0, 0))
  protected def elideSettings(extendedSettings: LowLevelCanvas.ExtendedSettings): Canvas.Settings =
    extendedSettings.settings

  /** The settings applied to this canvas.
    */
  final def canvasSettings: Canvas.Settings = settings
}

object LowLevelCanvas {

  /** Returns a new [[LowLevelCanvas]] for the default backend for the target platform.
    *
    * @return [[LowLevelCanvas]] using the default backend for the target platform
    */
  def create()(using backend: DefaultBackend[Any, LowLevelCanvas]): LowLevelCanvas =
    backend.defaultValue()

  /** Internal data structure containing canvas settings and precomputed values.
    */
  final case class ExtendedSettings(
      settings: Canvas.Settings,
      windowWidth: Int,
      windowHeight: Int
  ) {
    val scale = settings.scale match {
      case Some(scale)                  => scale
      case None if !settings.fullScreen => 1
      case _ =>
        val wScale = windowWidth / settings.width
        val hScale = windowHeight / settings.height
        Math.max(1, Math.min(wScale, hScale))
    }
    val scaledWidth  = settings.width * scale
    val scaledHeight = settings.height * scale
    val canvasX      = (windowWidth - scaledWidth) / 2
    val canvasY      = (windowHeight - scaledHeight) / 2
  }

  object ExtendedSettings {
    def apply(settings: Canvas.Settings): ExtendedSettings =
      ExtendedSettings(
        settings,
        settings.width * settings.scale.getOrElse(1),
        settings.height * settings.scale.getOrElse(1)
      )
  }
}
