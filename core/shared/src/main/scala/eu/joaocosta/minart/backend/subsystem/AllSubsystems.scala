package eu.joaocosta.minart.backend.subsystem

import eu.joaocosta.minart.audio._
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.input._

/** Internal object with an intersection of all subsystems.
  */
private[minart] class AllSubsystems(canvas: LowLevelCanvas, audioPlayer: LowLevelAudioPlayer)
    extends LowLevelSubsystem.Composite[Canvas.Settings, AudioPlayer.Settings, LowLevelCanvas, LowLevelAudioPlayer](
      canvas,
      audioPlayer
    )
    with Canvas
    with AudioPlayer {

  // Canvas
  def canvasSettings: Canvas.Settings = canvas.canvasSettings
  def changeSettings(newSettings: Canvas.Settings): Unit =
    canvas.changeSettings(newSettings)
  def clear(buffers: Set[Canvas.Buffer]): Unit = canvas.clear(buffers)
  def getKeyboardInput(): KeyboardInput        = canvas.getKeyboardInput()
  def getPointerInput(): PointerInput          = canvas.getPointerInput()
  def redraw(): Unit                           = canvas.redraw()

  // MutableSurface
  override def fill(color: Color): Unit                              = canvas.fill(color)
  def fillRegion(x: Int, y: Int, w: Int, h: Int, color: Color): Unit = canvas.fillRegion(x, y, w, h, color)
  def putPixel(x: Int, y: Int, color: Color): Unit                   = canvas.putPixel(x, y, color)
  override def blit(
      that: Surface,
      mask: Option[Color] = None
  )(x: Int, y: Int, cx: Int = 0, cy: Int = 0, cw: Int = that.width, ch: Int = that.height): Unit =
    canvas.blit(that, mask)(x, y, cx, cy, cw, ch)

  // Surface
  override def getPixels(): Vector[Array[Color]] = canvas.getPixels()
  def unsafeGetPixel(x: Int, y: Int): Color      = canvas.unsafeGetPixel(x, y)

  // AudioPlayer
  def isPlaying(): Boolean                               = audioPlayer.isPlaying()
  override def play(clip: AudioClip): Unit               = audioPlayer.play(clip)
  def play(clip: AudioClip, channel: Int): Unit          = audioPlayer.play(clip, channel)
  override def play(wave: AudioWave): Unit               = audioPlayer.play(wave)
  override def play(wave: AudioWave, channel: Int): Unit = audioPlayer.play(wave, channel)
  def stop(): Unit                                       = audioPlayer.stop()
  def stop(channel: Int): Unit                           = audioPlayer.stop(channel)
}
