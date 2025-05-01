# 4. Pointer input

Now that we learned the basics of animation, we can start to look at more dynamic applications.

What we need now is to handle some input. Let's see how that's done with a simple example that uses the mouse position and button presses.

## Pointer tracking

### Dependencies and imports

As before, let's import the backend, graphics and runtime.

We also need to import the input package. We need this to read data from input devices, such as keyboard and mouse.

```scala
//> using scala "3.3.5"
//> using dep "eu.joaocosta::minart::0.6.3"

import eu.joaocosta.minart.backend.defaults.given
import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.input.*
import eu.joaocosta.minart.runtime.*
```

### Drawing the cursor

For this example, we will write a simple application that puts a pixel in the cursor position.

If the pointer input is pressed, the pixel is red, otherwise it is black.

First we then fetch the input data with `canvas.getPointerInput()`, to read the `position` and `isPressed` property.

Notice that now we are calling `canvas.clear()` at the beginning of each frame.
This not only clears the image with the clear color, but also clears some information regarding the input, such as which keys and buttons were pressed on the previous frame.

> [!NOTE]
> Since `canvas.clear()` clears some input information, it is usually recommended to store the input in a variable right before calling `clear()`,
> and then process the input.

```scala
def application(canvas: Canvas): Unit = {
  val mouse = canvas.getPointerInput()

  canvas.clear()

  val mouseColor =
    if (mouse.isPressed) Color(255, 0, 0)
    else Color(0, 0, 0)

  mouse.position.foreach(pos => canvas.putPixel(pos.x, pos.y, mouseColor))

  canvas.redraw()
}
```

> [!NOTE]
> In this example, we call `canvas.clear()` in the beginning and `canvas.redraw()` at the end.
> This will reduce the frame latency but increase frame jitter. In some situations, you might prefer to call
> `redraw()` at the beginning of the frame, followed immediately by a `clear()`.


### Application Loop

Then we simply run our application.

Note that we now also added a `clearColor` to our canvas settings. This is the color that is rendered when the canvas is cleared.

```scala
val canvasSettings = Canvas.Settings(width = 128, height = 128, scale = Some(4), clearColor = Color(255, 255, 255))

AppLoop
  .statelessRenderLoop(application)
  .configure(canvasSettings, LoopFrequency.Uncapped) // As fast as possible
  .run()
```
