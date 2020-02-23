package eu.joaocosta.minart

/**
 * Abstraction that provides `init` and `destroy` operations to
 * create and destroy canvas windows.
 */
trait CanvasManager { canvas: Canvas =>

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
   * Rendering operations can only be called after calling this.
   *
   * @return canvas object linked to the created window
   */
  def init(): Canvas = {
    if (!created) {
      unsafeInit()
      created = true
    }
    canvas
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
