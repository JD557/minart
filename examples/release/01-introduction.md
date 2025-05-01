# 1. Introduction

Welcome to the Minart tutorial!

Minart is a Scala library to for creative coding.
In this example, we are going to show how to draw some colors on the screen.

Note that this first example will only work on the JVM. Don't worry, soon we will cover cross-compilation.

## Running the examples

You can run the code in this examples with [Scala CLI](https://scala-cli.virtuslab.org/).
For example, you can run this file with:

```
scala-cli 01-introduction.md
```

## Showing a simple colored square

### Dependencies and imports

The simplest way to use Minart is to simply include the `minart` library, which packages all modules.

```scala
//> using scala "3.3.5"
//> using dep "eu.joaocosta::minart::0.6.3"
```

As for the imports, the `eu.joaocosta.minart.backend.defaults` package contains the givens with the backend-specific (JVM/JS/Native) logic.
If for some reason you forget to import this, don't worry, you should get a helpful warning message to remind you.

Since we want to work with graphics, we also need to import eu.joaocosta.minart.graphics._

```scala
import eu.joaocosta.minart.backend.defaults.given
import eu.joaocosta.minart.graphics.*
```

### Creating a Canvas


The `Canvas.Settings` are the settings of our window.

Those will be covered in more detail in a future example. Right now, you can see that this will create a 512 x 512
window with a 128 x 128 canvas (each pixel in the canvas will be a 4 x 4 square on the screen).

```scala
val canvasSettings = Canvas.Settings(width = 128, height = 128, scale = Some(4))
```

Then we need to create our Canvas.
This is quite a low-level way to do it, but it can be helpful to understand how things work.

```scala
val canvas = LowLevelCanvas.create()
canvas.init(canvasSettings)
```

### Drawing

Here comes the drawing part.

For each pixel on the screen, we will write a different color based on the x and y position.
We can write colors using the `Canvas#putPixel` operation.

```scala
for {
  x <- (0 until canvas.width)
  y <- (0 until canvas.height)
} {
  val color =
    Color((255 * x.toDouble / canvas.width).toInt, (255 * y.toDouble / canvas.height).toInt, 255)
  canvas.putPixel(x, y, color)
}
```

Now, this alone won't draw to the screen, we need to call redraw to update what's shown.

```scala
canvas.redraw()
```

Finally, we close the canvas.
Let's close it after waiting a few seconds.

```scala
Thread.sleep(5000)
canvas.close()
```
