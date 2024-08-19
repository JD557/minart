# 3. Animation

In the previous examples we just drew a static image on the screen.

This time, we'll write an animated fire, updating at 60 frames per second!

## Animated fire

### Dependencies and imports

As before, let's import the backend, graphics and runtime.

```scala
//> using scala "3.3.3"
//> using dep "eu.joaocosta::minart::0.6.1"

import eu.joaocosta.minart.backend.defaults.given
import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.runtime.*
```

### Fire automata

This is just the function to define the fire animation.

For each pixel we look at the 3 pixels below (using `Canvas#getPixel`) and return the next color accordingly.

The details are not very important. Feel free to skip over this step (or to play around with the numbers).

```scala
def automata(canvas: Canvas, x: Int, y: Int): Color = {
  // For each pixel, we fetch the colors 3 pixels below (SW, S, SE)
  val neighbors = (x - 1 to x + 1).toList.flatMap { xx =>
    canvas.getPixel(xx, y + 1)
  }

  // We compute some random loss
  val randomLoss = 0.8 + (scala.util.Random.nextDouble() / 5)

  // The pixel temperature will be the average of the neighbors intensity, with the random loss applied
  val temperature = ((neighbors.map(c => (c.r + c.g + c.b) / 3).sum / 3) * randomLoss).toInt

  // Then we generate a nice yelow-red tint based on the temperature
  Color(
    Math.min(255, temperature * 1.6).toInt,
    (temperature * 0.8).toInt,
    (temperature * 0.6).toInt
  )
}
```

### Application Loop

Now, here's the important part.

Instead of using the `LoopFrequency.Never`, we now use the `LoopFrequency.hz60`.
This will run our rendering function on each frame, with a specified delay to lock the frame rate at 60 FPS.
We could also have used `LoopFrequency.Uncapped` to not cap the frame rate.

```scala
val canvasSettings = Canvas.Settings(width = 128, height = 128, scale = Some(4))

AppLoop
  .statelessRenderLoop((canvas: Canvas) => {
    // We set some pixels to always be white, so that the fire keeps growing from there
    // Add bottom fire root
    for {
      x <- (0 until canvas.width)
      y <- (canvas.height - 4 until canvas.height)
    } {
      canvas.putPixel(x, y, Color(255, 255, 255))
    }

    // Add middle fire root
    for {
      x <- (0 until canvas.width)
      y <- (0 until canvas.height)
      dist = Math.pow(x - canvas.width / 2, 2) + Math.pow(y - canvas.height / 2, 2)
    } {
      if (dist > 25 && dist <= 100) canvas.putPixel(x, y, Color(255, 255, 255))
    }

    for {
      x <- (0 until canvas.width)
      y <- (0 until (canvas.height - 1)).reverse
    } {
      val color = automata(canvas, x, y)
      canvas.putPixel(x, y, color)
    }
    canvas.redraw()
  })
  .configure(canvasSettings, LoopFrequency.hz60)
  .run()
```
