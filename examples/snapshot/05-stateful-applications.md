# 5. Stateful applications

Now that we learned the basics of animation and input handling, we are almost ready to make interactive applications, such as a game.

The only thing missing is a way to handle state. So far, all our applications were mostly stateless (using only the state from the Canvas).

In this example, we will show how to write applications that manipulate a state variable. To do that, we will write a simple snake game.

## Snake game

### Dependencies and imports

The dependencies will be the same as before. We also include Scala's `Random` here just to make the game more interesting.

```scala
//> using scala "3.3.7"
//> using dep "eu.joaocosta::minart::0.6.6-SNAPSHOT"

import scala.util.Random

import eu.joaocosta.minart.backend.defaults.given
import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.input.*
import eu.joaocosta.minart.runtime.*
```

### Game State

Here we define our game state.

We need to keep track of:
- The board state, which contains which tiles have a part of the snake. Each tile will contain a number with the remaining "lifetime" of the tile.
- The positions for the snake head and the apple
- The current snake direction
- The snake size

```scala

final case class Position(x: Int, y: Int)

enum Direction(val x: Int, val y: Int) {
  case Up    extends Direction(0, -1)
  case Down  extends Direction(0, 1)
  case Left  extends Direction(-1, 0)
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
  lazy val gameOver = board(snakeHead.y)(snakeHead.x) != 0
}
```

Then we define a method to update our state and get the next state:

```scala
def mod(x: Int, y: Int): Int =
  if (x >= 0) x % y
  else mod(x + y, y)

def nextState(state: GameState): GameState = {
  val hitApple = state.applePos == state.snakeHead
  val newApplePos =
    if (hitApple)
      Position(Random.nextInt(state.boardWidth - 1), Random.nextInt(state.boardHeight - 1))
    else state.applePos
  val newSnakeSize =
    if (hitApple) state.snakeSize + 1
    else state.snakeSize
  val newBoard = state.board
    .updated(state.snakeHead.y, state.board(state.snakeHead.y).updated(state.snakeHead.x, state.snakeSize))
    .map(_.map(life => Math.max(0, life - 1)))
  val newSnakeHead = Position(
    mod(state.snakeHead.x + state.snakeDir.x, state.boardWidth),
    mod(state.snakeHead.y + state.snakeDir.y, state.boardHeight)
  )
  state.copy(
    board = newBoard,
    snakeHead = newSnakeHead,
    applePos = newApplePos,
    snakeSize = newSnakeSize
  )
}
```

### Handling input

We also need to handle the keyboard input. For this, we will write a method that receives a `GameState` and a `KeyboardInput` and updates the state accordingly.

We use the `isDown` method to find out if a key is pressed, and adjust the snake direction accordingly.

```scala
def processInput(state: GameState, keyboardInput: KeyboardInput): GameState = {
  if (keyboardInput.isDown(KeyboardInput.Key.Up)) state.copy(snakeDir = Direction.Up)
  else if (keyboardInput.isDown(KeyboardInput.Key.Down)) state.copy(snakeDir = Direction.Down)
  else if (keyboardInput.isDown(KeyboardInput.Key.Left)) state.copy(snakeDir = Direction.Left)
  else if (keyboardInput.isDown(KeyboardInput.Key.Right)) state.copy(snakeDir = Direction.Right)
  else state
}
```

### Putting it all together

Now it's time to put it all together. First let's define our canvas settings an initial state.

We will use a scale of 16x, so that each pixel is really visible.

```scala

val canvasSettings = Canvas.Settings(width = 32, height = 32, scale = Some(16), clearColor = Color(0, 0, 0))
val initialState = GameState(Vector.fill(canvasSettings.height)(Vector.fill(canvasSettings.width)(0)))
```

Then we define our application logic. Note that this time our logic takes both a `GameState` and a `Canvas`

```scala
def application(state: GameState, canvas: Canvas): GameState = {
  canvas.clear()

  // Draw the snake body
  for {
    (line, y)  <- state.board.zipWithIndex
    (value, x) <- line.zipWithIndex
    if (value > 0)
  } canvas.putPixel(x, y, Color(0, (55 + (200 * value / state.snakeSize)), 0))

  // Then the snake head
  canvas.putPixel(state.snakeHead.x, state.snakeHead.y, Color(0, 255, 0))

  // Then a red apple
  canvas.putPixel(state.applePos.x, state.applePos.y, Color(255, 0, 0))

  // Then redraw the canvas
  canvas.redraw()

  /*
   * Finally, we fetch the keyboard input with getKeyboardInput() and compute the new state.
   */
  if (state.gameOver) initialState
  else nextState(processInput(state, canvas.getKeyboardInput()))
}
```

Now, since we want to keep a game state between frames, we need to use the `statefulRenderLoop`.
This is very similar to the `statelessRenderLoop`, but now our render function takes a canvas and a state, and it returns the next state.

> [!NOTE]
> Technically, it would also be possible to use the `statelessRenderLoop` with a `var state: GameState = initialState`.
>
> However, even though Minart provides mutable imperative APIs, it is still recommended to use immutability when possible.
> Still, if you prefer to use mutate global variables, nothing stops you from doing it.

```scala
AppLoop
  .statefulRenderLoop((state: GameState) => (canvas: Canvas) => application(state, canvas))
  .configure(
    canvasSettings,
    LoopFrequency.fromHz(15),
    initialState              // Notice that the configure function now needs an initialState
  )
  .run()
```
