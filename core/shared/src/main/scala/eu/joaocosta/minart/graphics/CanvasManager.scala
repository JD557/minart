package eu.joaocosta.minart.graphics

import eu.joaocosta.minart.backend.defaults.DefaultBackend

/** Abstraction that provides `init` and `destroy` operations to
  * create and destroy canvas windows.
  */
trait CanvasManager {

  /** Creates the canvas window and returns a low-level canvas object.
    *
    * @return low-level canvas object linked to the created window
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

  /** Returns [[CanvasManager]] for the default backend for the target platform.
    *
    * @return [[CanvasManager]] using the default backend for the target platform
    */
  def apply(): CanvasManager =
    CanvasManager(() => LowLevelCanvas.create())
}
