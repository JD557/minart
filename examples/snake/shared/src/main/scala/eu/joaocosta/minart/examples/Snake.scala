package eu.joaocosta.minart.examples

import scala.util.Random

import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.core._

object Snake {

  def mod(x: Int, y: Int): Int =
    if (x >= 0) x % y
    else mod(x + y, y)

  case class GameState(
      board: Vector[Vector[Int]],
      snakeHead: (Int, Int) = (0, 0),
      snakeDir: (Int, Int) = (1, 0),
      applePos: (Int, Int) = (10, 10),
      snakeSize: Int = 10
  ) {

    lazy val boardWidth  = board.head.size
    lazy val boardHeight = board.size

    lazy val nextState: GameState = {
      val (newApplePos, newSnakeSize) =
        if (applePos == snakeHead)
          (Random.nextInt(boardWidth - 1), Random.nextInt(boardHeight - 1)) -> (snakeSize + 1)
        else applePos                                                       -> snakeSize
      copy(
        board = board
          .updated(snakeHead._2, board(snakeHead._2).updated(snakeHead._1, snakeSize))
          .map(_.map(x => if (x > 0) x - 1 else x)),
        snakeHead = (mod(snakeHead._1 + snakeDir._1, boardWidth), mod(snakeHead._2 + snakeDir._2, boardHeight)),
        applePos = newApplePos,
        snakeSize = newSnakeSize
      )
    }

    def updateSnakeDir(keyboardInput: KeyboardInput): GameState = {
      val newYDir =
        if (keyboardInput.isDown(KeyboardInput.Key.Up)) -1
        else if (keyboardInput.isDown(KeyboardInput.Key.Down)) 1
        else 0
      val newXDir =
        if (keyboardInput.isDown(KeyboardInput.Key.Left)) -1
        else if (keyboardInput.isDown(KeyboardInput.Key.Right)) 1
        else 0
      val newDir =
        if (newXDir != 0) (newXDir, 0)
        else if (newYDir != 0) (0, newYDir)
        else snakeDir
      copy(snakeDir = newDir)
    }

    lazy val gameOver: Boolean = board(snakeHead._2)(snakeHead._1) > 0
  }

  val canvasSettings = Canvas.Settings(width = 32, height = 32, scale = 16, clearColor = Color(0, 0, 0))

  def main(args: Array[String]): Unit = {
    val initialState = GameState(Vector.fill(canvasSettings.height)(Vector.fill(canvasSettings.width)(0)))

    RenderLoop
      .default()
      .infiniteRenderLoop[GameState](
        (c, state) => {
          c.clear()

          for {
            (line, y)  <- state.board.zipWithIndex
            (value, x) <- line.zipWithIndex
            if (value > 0)
          } c.putPixel(x, y, Color(0, (55 + (200 * value / state.snakeSize)), 0))

          c.putPixel(state.snakeHead._1, state.snakeHead._2, Color(0, 255, 0))
          c.putPixel(state.applePos._1, state.applePos._2, Color(255, 0, 0))

          c.redraw()
          if (state.gameOver) initialState
          else state.updateSnakeDir(c.getKeyboardInput()).nextState
        },
        FrameRate.fromFps(15)
      )(
        CanvasManager.default(),
        canvasSettings,
        initialState
      )
  }
}
