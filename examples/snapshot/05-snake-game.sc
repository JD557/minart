//> using scala "3.3.1"
//> using lib "eu.joaocosta::minart::0.5.4-SNAPSHOT"

/*
 * Now that we learned the basics of animation and input handling, we are almost ready to make a game.
 *
 * What we need now is to handle state. Let's see how that's done.
 */

/*
 * Let's start with the usual imports.
 */
import scala.util.Random

import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.input._
import eu.joaocosta.minart.runtime._

/*
 * Again, let's define our canvas settings.
 * This time we'll use a smaller resolution with a larger scale.
 */
val canvasSettings = Canvas.Settings(width = 32, height = 32, scale = Some(16), clearColor = Color(0, 0, 0))

/**
 * Then we define our game logic, this is an implementation of the classic snake game.
 */
def mod(x: Int, y: Int): Int =
  if (x >= 0) x % y
  else mod(x + y, y)

final case class Position(x: Int, y: Int)

enum Direction(val x: Int, val y: Int) {
  case Up extends Direction(0, -1)
  case Down extends Direction(0, 1)
  case Left extends Direction(-1, 0)
  case Right extends Direction(1, 0)
}

final case class GameState(
    board: Vector[Vector[Int]],
    snakeHead: Position = Position(0, 0),
    snakeDir: Direction = Direction.Right,
    applePos: Position = Position(10, 10),
    snakeSize: Int = 10
) {

  lazy val boardWidth  = board.head.size
  lazy val boardHeight = board.size

  lazy val nextState: GameState = {
    val newApplePos = 
      if (applePos == snakeHead) Position(Random.nextInt(boardWidth - 1), Random.nextInt(boardHeight - 1))
      else applePos
    val newSnakeSize =
      if (applePos == snakeHead) snakeSize + 1
      else snakeSize
    val newBoard = board
        .updated(snakeHead.y, board(snakeHead.y).updated(snakeHead.x, snakeSize))
        .map(_.map(life => Math.max(0, life - 1)))
    copy(
      board = newBoard,
      snakeHead = Position(mod(snakeHead.x + snakeDir.x, boardWidth), mod(snakeHead.y + snakeDir.y, boardHeight)),
      applePos = newApplePos,
      snakeSize = newSnakeSize
    )
  }

  /*
   * Notice the use of the KeyboardInput here.
   * We use the isDown method to find out if a key is pressed, and adjust the snake direction accordingly.
   */
  def updateSnakeDir(keyboardInput: KeyboardInput): GameState = {
    if (keyboardInput.isDown(KeyboardInput.Key.Up)) copy(snakeDir = Direction.Up)
    else if (keyboardInput.isDown(KeyboardInput.Key.Down)) copy(snakeDir = Direction.Down)
    else if (keyboardInput.isDown(KeyboardInput.Key.Left)) copy(snakeDir = Direction.Left)
    else if (keyboardInput.isDown(KeyboardInput.Key.Right)) copy(snakeDir = Direction.Right)
    else this
  }

  lazy val gameOver: Boolean = board(snakeHead.y)(snakeHead.x) != 0
}

// Initial state with an empty game board
val initialState = GameState(Vector.fill(canvasSettings.height)(Vector.fill(canvasSettings.width)(0)))

/*
 * Now, since we want to keep a game state between frames, we need to use the statefulRenderLoop.
 * This is very similar to the statelessRenderLoop, but now our render function takes a canvas and a state,
 * and it returns the next state.
 */
AppLoop
  .statefulRenderLoop(
    (state: GameState) => (canvas: Canvas) => {
      canvas.clear()

      // We draw the snake body
      for {
        (line, y)  <- state.board.zipWithIndex
        (value, x) <- line.zipWithIndex
        if (value > 0)
      } canvas.putPixel(x, y, Color(0, (55 + (200 * value / state.snakeSize)), 0))

      // Then the snake head
      canvas.putPixel(state.snakeHead.x, state.snakeHead.y, Color(0, 255, 0))

      // Then a nice red apple
      canvas.putPixel(state.applePos.x, state.applePos.y, Color(255, 0, 0))
      
      // Then redraw the canvas
      canvas.redraw()

      /*
       * Finally, we fetch the keyboard input with getKeyboardInput() and compute the new state.
       */
      if (state.gameOver) initialState
      else state.updateSnakeDir(canvas.getKeyboardInput()).nextState
    },
  )
  .configure(
    canvasSettings,
    LoopFrequency.fromHz(15), // 15 FPS
    initialState // Notice that the configure function now needs an initialState
  )
  .run()
