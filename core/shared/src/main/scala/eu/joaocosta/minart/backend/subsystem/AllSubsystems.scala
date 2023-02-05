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
  def fill(color: Color): Unit                     = canvas.fill(color)
  def putPixel(x: Int, y: Int, color: Color): Unit = canvas.putPixel(x, y, color)

  // Surface
  def getPixels(): Vector[Array[Color]]     = canvas.getPixels()
  def unsafeGetPixel(x: Int, y: Int): Color = canvas.unsafeGetPixel(x, y)

  // AudioPlayer
  def isPlaying(): Boolean                      = audioPlayer.isPlaying()
  def play(wave: AudioClip): Unit               = audioPlayer.play(wave)
  def play(wave: AudioClip, channel: Int): Unit = audioPlayer.play(wave, channel)
  def stop(): Unit                              = audioPlayer.stop()
  def stop(channel: Int): Unit                  = audioPlayer.stop(channel)
}
