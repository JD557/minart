package eu.joaocosta.minart.runtime.pure

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.graphics.pure._
import eu.joaocosta.minart.runtime._

/** Entrypoint for pure Minart applications. */
trait MinartApp {
  type State
  def canvasManager: CanvasManager
  def canvasSettings: Canvas.Settings
  def loopRunner: LoopRunner
  def initialState: State
  def renderFrame: State => CanvasIO[State]
  def terminateWhen: State => Boolean
  def frameRate: LoopFrequency

  def main(args: Array[String]): Unit = {
    PureRenderLoop
      .finiteRenderLoop[State](renderFrame, terminateWhen, frameRate)(
        loopRunner,
        canvasManager,
        canvasSettings,
        initialState
      )
  }
}
