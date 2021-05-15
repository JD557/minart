package eu.joaocosta.minart.backend

import org.scalajs.dom

import scala.annotation.tailrec
import scala.concurrent.duration.FiniteDuration
import scala.scalajs.js.{isUndefined, timers}

import eu.joaocosta.minart.core._
import eu.joaocosta.minart.core.RenderLoop._

object JsRenderLoop extends ImpureRenderLoop {
  lazy val hasWindow = !isUndefined(dom.window)

  def finiteRenderLoop[S](
      renderFrame: (Canvas, S) => S,
      terminateWhen: S => Boolean,
      frameRate: FrameRate
  ): StatefulRenderLoop[S] = {
    val frameMillis = frameRate match {
      case FrameRate.Uncapped          => 0
      case FrameRate.FrameDuration(ms) => ms
    }
    new StatefulRenderLoop[S] {
      def apply(canvasManager: CanvasManager, canvasSettings: Canvas.Settings, initialState: S) = {
        val canvas = canvasManager.init(canvasSettings)
        def finiteRenderLoopAux(state: S): Unit = {
          val startTime = System.currentTimeMillis()
          val newState  = renderFrame(canvas, state)
          if (!terminateWhen(newState) && canvas.isCreated()) {
            val endTime  = System.currentTimeMillis()
            val waitTime = frameMillis - (endTime - startTime)
            if (waitTime > 0 || !hasWindow) timers.setTimeout(waitTime.toDouble)(finiteRenderLoopAux(newState))
            else dom.window.requestAnimationFrame((_: Double) => finiteRenderLoopAux(newState))
          } else if (canvas.isCreated()) canvas.destroy()
        }
        finiteRenderLoopAux(initialState)
      }
    }
  }

  def singleFrame(renderFrame: Canvas => Unit): StatelessRenderLoop = new StatelessRenderLoop {
    def apply(canvasManager: CanvasManager, canvasSettings: Canvas.Settings) = {
      val canvas = canvasManager.init(canvasSettings)
      renderFrame(canvas)
    }
  }
}
