# Minart

![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/eu.joaocosta/minart-core_2.13?server=https%3A%2F%2Foss.sonatype.org)

Minart is a very minimalistic Scala library to put pixels in a canvas.

It's mostly useful for small toy projects or prototypes that deal with
generative art or software rendering.

I wouldn't recommend using this in any serious production environment.

## Features

* JVM and JS support
* Small footprint
* Double buffered canvas
* Integer scaling

## Sample Usage

To include minart, simply add `minart-core` to your project:

```scala
libraryDependencies += "eu.joaocosta" %% "minart-core" % "0.1.0"
```

The `examples` folder contains some sample code.

The main concept behind Minart is the `Canvas` object.
Creating a `Canvas` object is a side-effectful operation that
creates a new window and provides the following operations:

* `putPixel(x: Int, y: Int, color: Color): Unit`
* `getBackbufferPixel(x: Int, y: Int): Color`
* `clear(): Unit`
* `redraw(): Unit`

Here's a simple example that renders a color gradient to a screen:

```scala
val canvas = new AwtCanvas(128, 128, scale = 4) // This would be an HtmlCanvas in scala.js

for {
  x <- (0 until canvas.width)
  y <- (0 until canvas.height)
} {
  val color = Color((255 * x.toDouble / canvas.width).toInt, (255 * y.toDouble / canvas.height).toInt, 255)
  canvas.putPixel(x, y, color)
}
canvas.redraw()
```

There's also a `RenderLoop` object on each platform (`JavaRenderLoop` and `JsRenderLoop`)
with helpful methods to implement simple render loops:

* `finiteRenderLoop[S](initialState: S, renderFrame: S => S, terminateWhen: S => Boolean, frameDuration: FiniteDuration): Unit`
* `infiniteRenderLoop[S](initialState: S, renderFrame: S => S, frameDuration: FiniteDuration): Unit`
* `infiniteRenderLoop(renderFrame: Unit => Unit, frameDuration: FiniteDuration): Unit`
