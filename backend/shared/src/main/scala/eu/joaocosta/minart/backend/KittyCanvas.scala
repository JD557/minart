package eu.joaocosta.minart.backend

import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.input.*

/** A low level Canvas implementation that shows the image using the Kitty image protocol.
  */
final class KittyCanvas(imageId: Int = scala.util.Random.nextInt(100)) extends SurfaceBackedCanvas {

  // Rendering resources

  protected var surface: RamSurface = _
  private val b64                   = java.util.Base64.getEncoder()

  // Input resources

  // Initialization

  def this(settings: Canvas.Settings) = {
    this()
    this.init(settings)
  }

  protected def unsafeInit(): Unit = {}

  protected def unsafeApplySettings(newSettings: Canvas.Settings): LowLevelCanvas.ExtendedSettings = {
    val extendedSettings = LowLevelCanvas.ExtendedSettings(newSettings)
    surface = new RamSurface(newSettings.width, newSettings.height, newSettings.clearColor)
    // Clear and reset the scroll
    println("\u001b[2J")
    println("\u001b[H")
    extendedSettings
  }

  // Cleanup

  protected def unsafeDestroy(): Unit = {
    // Clear and reset the scroll
    println("\u001b[2J")
    println("\u001b[H")
  }

  // Canvas operations

  def clear(buffers: Set[Canvas.Buffer]): Unit = {
    if (buffers.contains(Canvas.Buffer.KeyboardBuffer)) {}
    if (buffers.contains(Canvas.Buffer.PointerBuffer)) {}
    if (buffers.contains(Canvas.Buffer.Backbuffer)) {
      surface.fill(settings.clearColor)
    }
  }

  def redraw(): Unit = try {
    val scale = settings.scale.getOrElse(1)
    print("\u001b")
    print(s"_Gf=32,a=T,s=${width * scale},v=${height * scale},C=1;")
    print(
      b64.encodeToString(
        surface.view
          .scale(scale)
          .toRamSurface()
          .dataBuffer
          .flatMap(c => Array(c.r.toByte, c.g.toByte, c.b.toByte, c.a.toByte))
      )
    )
    print("\u001b")
    print("\\")
  } catch { case _: Throwable => () }

  def getKeyboardInput(): KeyboardInput = KeyboardInput.empty
  def getPointerInput(): PointerInput   = PointerInput.empty
}
