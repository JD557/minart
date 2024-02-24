# 6. Surfaces

Writing directly to a canvas pixel by pixel worked fine in the previous examples, but sometimes it's helpful to store some full images to draw.

This is where surfaces come in.

A `Surface` is something with a `getPixel` operation (`MutableSurface`s also provide a `putPixel` operation) along other helpful methods.

> [!NOTE]
> A `Canvas` is also a `MutableSurface`.

## Moving sprites

### Dependencies and imports

For this example, we just need to use the graphics and runtime

```scala
//> using scala "3.3.1"
//> using dep "eu.joaocosta::minart::0.6.0-M2"


import eu.joaocosta.minart.backend.defaults.given
import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.runtime.*
```

### Creating a new Surface

One of the most simple, but also most versatile surfaces in Minart is the `RamSurface`.

This surface is simply a 2D array storing colors in RAM. Creating one is pretty easy if we have a `Seq[Seq[Color]]`.

In this example we will draw a colorful square with an all-black diagonal.

```scala
val image = new RamSurface(
  Vector.tabulate(16, 16) { (y, x) =>
    if ((x + y) < 16) Color(0, 0, 0)
    else Color((16 * x.toDouble).toInt, (16 * y.toDouble).toInt, 255)
  }
)
```

Along with a colorful background. In this case, we will create the surface in a different way - we first create an empty surface with the desired size, and then fill it with `putPixel`.

```scala
// We are defining the canvas settings here for convenience
val canvasSettings = Canvas.Settings(width = 128, height = 128, scale = Some(4))

val background: Surface = {
  val surface = new RamSurface(canvasSettings.width, canvasSettings.height, Color(0, 0, 0))
  for {
    x <- (0 until surface.width)
    y <- (0 until surface.height)
  } {
    val color =
      Color((255 * x.toDouble / surface.width).toInt, (255 * y.toDouble / surface.height).toInt, 255)
    surface.putPixel(x, y, color)
  }
  surface
}
```

### Drawing surfaces

A surface can be drawn on top of a mutable surface with the `blit` operation.

Here's how we could draw the background in the canvas, at position (0, 0):

```scala
def drawBackground(canvas: Canvas): Unit = {
  canvas.blit(background)(0, 0)
}
```

The `blit` operation does allow for more advanced use cases, such as clipping.

For example, say we want to draw our sprite at position (t, t), but we only want to draw the 8x8 portion starting at the pixel (4, 4), we can do:

```scala
def drawSprite(canvas: Canvas, t: Int): Unit = {
  canvas.blit(image)(t, t, 4, 4, 8, 8)
}
```

It also supports an optional blend mode.
For example, using `BlendMode.ColorMask`, pixels with the mask color are ignored, working as a transparent.

> [!NOTE]
> Check the `BlendMode` documentation to learn about the other blend modes.

Here, let's apply a mask that makes the black corner transparent.

```scala
def blendSprite(canvas: Canvas, t: Int): Unit = {
  canvas.blit(image, BlendMode.ColorMask(Color(0, 0, 0)))(111 - t, t, 4, 4, 8, 8)
}
```

### Putting it all together

```scala
AppLoop
  .statefulRenderLoop((t: Int) =>
    (canvas: Canvas) => {
      drawBackground(canvas)
      drawSprite(canvas, t)
      blendSprite(canvas, t)
      canvas.redraw()
      (t + 1) % (128 - 16)
    }
  )
  .configure(
    canvasSettings,
    LoopFrequency.hz60,
    0
  )
  .run()
```
