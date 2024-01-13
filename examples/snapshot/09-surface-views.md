# 9. Surface views


It can be quite cumbersome an inefficient to apply multiple transformations to a surface if we just use the `getPixel`
and `putPixel` operations.

Surface Views and Planes are a way to manipulate surfaces with operations like `map`, `contramap`, etc without allocating
intermediate results.
This tutorial will show how to use those

## Rotating image

### Dependencies and imports

```scala
//> using scala "3.3.1"
//> using lib "eu.joaocosta::minart::0.6.0-SNAPSHOT"

import eu.joaocosta.minart.backend.defaults.given
import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.graphics.image.*
import eu.joaocosta.minart.runtime.*
```

### Surface views

First, let's load our example Scala logo image.

We'll use the Surface View API (by calling `.view`) to manipulate the image a bit, and then convert it back to a
`RamSurface`,

```scala
val bitmap         = Image.loadBmpImage(Resource("assets/scala.bmp")).get

val updatedBitmap = bitmap.view
  .map(color => if (color == Color(255, 255, 255)) Color(0, 0, 0) else color) // change background color
  .clip(14, 0, 100, 128)                                                      // clip the image
  .toRamSurface()                                                             // convert it back to a RamSurface
```

Note that in the above example we convert the image back to a RAM surface.

It is also possible to blit surface views, but that will apply the transformations everytime you draw the image.

That can be advantageous if the image changes on each frame or if most of the image will be off-canvas.

Since the image here is pretty small and static here, it's preferable to just allocate a new surface.

### Planes

Let's now talk about planes.

A plane can be seen as an unlimited surface view (basically, a `(Int, Int) => Color`).

This allows us to `contramap` surface views and handling infinite images.

Let's see an example with multiple effects, such as:
 - Rotation/Zoom
 - Blur
 - Wobble
 - Checkerboard invert

Before we start, let's precompute the convolution window that we will use for the blur

```scala
val convolutionWindow = for {
  x <- (-1 to 1)
  y <- (-1 to 1)
} yield (x, y)

```

Now, here's our application with all the effects:

```scala
def application(t: Double, canvas: Canvas): Unit = {
  val frameSin = Math.sin(t)
  val frameCos = Math.cos(t)
  val zoom     = 1.0 / (frameSin + 2.0)

  val image = updatedBitmap.view.repeating // Create an infinite Plane from our surface
    .scale(zoom, zoom)  // Scale
    .coflatMap { img => // Average blur
      convolutionWindow.iterator
        .map { case (x, y) => img(x, y) }
        .foldLeft(Color(0, 0, 0)) { case (Color(r, g, b), Color(rr, gg, bb)) =>
          Color(r + rr / 9, g + gg / 9, b + bb / 9)
        }
    }
    .rotate(t)                                                        // Rotate
    .contramap((x, y) => (x + (5 * Math.sin(t + y / 10.0)).toInt, y)) // Wobbly effect
    .flatMap(color =>
      (x, y) => // Checkerboard effect
        if (x % 32 < 16 != y % 32 < 16) color.invert
        else color
    )
    .clip(0, 0, 128, 128) // Clip into a SurfaceView

  canvas.blit(image)(0, 0)
}
```

### Putting it all together

```scala
val canvasSettings = Canvas.Settings(width = 128, height = 128, scale = Some(4), clearColor = Color(0, 0, 0))

AppLoop
  .statefulRenderLoop((t: Double) => (canvas: Canvas) => {
      canvas.clear()
      application(t, canvas)
      canvas.redraw()
      t + 0.01
    }
  )
  .configure(canvasSettings, LoopFrequency.hz60, 0)
  .run()
```
