package eu.joaocosta.minart.core

import eu.joaocosta.minart.backend.defaults.DefaultBackend

/** Abstraction that provides `init` and `destroy` operations to create and destroy canvas windows.
  */
trait CanvasManager {

  /** Creates the canvas window and returns a low-level canvas object.
    *
    * @return
    *   low-level canvas object linked to the created window
    */
  def init(settings: Canvas.Settings): LowLevelCanvas
}

object CanvasManager {

  def apply(canvasBuilder: () => LowLevelCanvas): CanvasManager = new CanvasManager {
    def init(settings: Canvas.Settings): LowLevelCanvas = {
      val canvas = canvasBuilder()
      canvas.init(settings)
      canvas
    }
  }

  implicit def defaultCanvasManager(implicit
      d: DefaultBackend[Any, LowLevelCanvas]
  ): DefaultBackend[Any, CanvasManager] = DefaultBackend.fromConstant(
    CanvasManager(() => d.defaultValue())
  )

  /** Returns [[CanvasManager]] for the default backend for the target platform.
    *
    * @return
    *   [[CanvasManager]] using the default backend for the target platform
    */
  def default()(implicit d: DefaultBackend[Any, CanvasManager]): CanvasManager =
    d.defaultValue()
}
