package eu.joaocosta.minart

/**
 * Canvas that can be painted.
 *
 * The Canvas is the main concept behind minart.
 *
 * A canvas represents a window with a `width * height` pixels.
 * There's also a `scale` variable that controls the integer scaling
 * and a `clearColor` that is applied to the whole canvas when it's cleared.
 *
 * Before using a Canvas, it's important to call the `init` operation to create
 * the window. There's also a `destroy` operation do destroy the window.
 */
trait Canvas extends RenderLoopCanvas {

  private[this] var created = false

  protected def unsafeInit(): Unit
  protected def unsafeDestroy(): Unit

  /**
   * Checks if the window is created or if it has been destroyed
   */
  def isCreated(): Boolean = created

  /**
   * Creates the canvas window.
   *
   * Rendering operations can only be called after calling this
   */
  def init(): Unit = if (!created) {
    unsafeInit()
    created = true
  }

  /**
   * Destroys the canvas window.
   *
   * Rendering operations cannot be called after calling this
   */
  def destroy(): Unit = if (created) {
    unsafeDestroy()
    created = false
  }
}
