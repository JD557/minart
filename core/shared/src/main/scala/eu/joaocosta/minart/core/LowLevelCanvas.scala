package eu.joaocosta.minart.core

import eu.joaocosta.minart.backend.defaults.DefaultBackend

/** A low-level version of a canvas that provides its own canvas manager.
  */
trait LowLevelCanvas extends Canvas {
  protected[this] var extendedSettings: Canvas.ExtendedSettings = _
  def settings: Canvas.Settings =
    if (extendedSettings == null) Canvas.Settings(0, 0)
    else extendedSettings.settings

  protected def unsafeInit(settings: Canvas.Settings): Unit
  protected def unsafeDestroy(): Unit

  /** Checks if the window is created or if it has been destroyed
    */
  def isCreated(): Boolean = !(extendedSettings == null)

  /** Creates the canvas window.
    *
    * Rendering operations can only be called after calling this.
    *
    * @return canvas object linked to the created window
    */
  def init(settings: Canvas.Settings): Unit = {
    if (isCreated) {
      destroy()
    }
    if (!isCreated) {
      unsafeInit(settings)
    }
  }

  /** Destroys the canvas window.
    *
    * Calling any operation on this canvas after calling destroy has an undefined behavior.
    */
  def destroy(): Unit = if (isCreated) {
    unsafeDestroy()
    extendedSettings = null
  }
}

object LowLevelCanvas {

  /** Returns [[LowLevelCanvas]] for the default backend for the target platform.
    *
    * @return [[LowLevelCanvas]] using the default backend for the target platform
    */
  def default()(implicit d: DefaultBackend[Any, LowLevelCanvas]): LowLevelCanvas =
    d.defaultValue()
}
