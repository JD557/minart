package eu.joaocosta.minart.core

import eu.joaocosta.minart.backend.defaults.DefaultBackend

/** Abstraction that provides `init` and `destroy` operations to
  * create and destroy canvas windows.
  */
trait CanvasManager { canvas: Canvas =>

  protected[this] var currentSettings: Canvas.Settings = null

  protected def unsafeInit(settings: Canvas.Settings): Unit
  protected def unsafeDestroy(): Unit

  /** Checks if the window is created or if it has been destroyed
    */
  def isCreated(): Boolean = !(currentSettings == null)

  /** Creates the canvas window.
    *
    * Rendering operations can only be called after calling this.
    *
    * @return canvas object linked to the created window
    */
  def init(settings: Canvas.Settings): Canvas = {
    if (isCreated && settings != currentSettings) {
      destroy()
    }
    if (!isCreated) {
      unsafeInit(settings = settings)
    }
    canvas
  }

  /** Destroys the canvas window.
    *
    * Rendering operations cannot be called after calling this
    */
  def destroy(): Unit = if (isCreated) {
    unsafeDestroy()
    currentSettings = null
  }
}

object CanvasManager {

  /** Returns [[CanvasManager]] for the default backend for the target platform.
    *
    * @return [[CanvasManager]] using the default backend for the target platform
    */
  def default()(implicit d: DefaultBackend[Any, CanvasManager]): CanvasManager =
    d.defaultValue()
}
