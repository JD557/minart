# Minart

![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/eu.joaocosta/minart-core_2.13?server=https%3A%2F%2Foss.sonatype.org)

Minart is a very minimalistic Scala library to put pixels in a canvas.

It's mostly useful for small toy projects or prototypes that deal with
generative art or software rendering.

I wouldn't recommend using this in any serious production environment.

## Features

* JVM, JS and Native support
* Small footprint
* Double buffered canvas
* Integer scaling

## Concepts

The 3 main concepts in Minart are:

### The CanvasManager

The `CanvasManager`, provides `init` and `destroy` operations to create
and delete a new window with a Canvas.

### The Canvas

The `Canvas`, which provides the operations required to draw on the window:
* `putPixel(x: Int, y: Int, color: Color): Unit`
* `getBackbufferPixel(x: Int, y: Int): Color`
* `clear(): Unit`
* `redraw(): Unit`

### The RenderLoop

The `RenderLoop`, which provides helpful operations to handle the render loops.

The provided methods take care of creating and destroying the window.

Note that `clear`/`redraw` still need to be called manually (since in some cases, it might be useful to not call those methods, especially `clear`)

* `finiteRenderLoop[S](initialState: S, renderFrame: S => S, terminateWhen: S => Boolean, frameRate: FrameRate): Unit`
* `infiniteRenderLoop[S](initialState: S, renderFrame: S => S, frameRate: FrameRate): Unit`
* `infiniteRenderLoop(renderFrame: Unit => Unit, frameRate: FrameRate): Unit`
* `singleFrame(canvasManager: CanvasManager, renderFrame: Canvas => Unit)`

## Sample Usage

To include minart, simply add `minart-core` to your project:

```scala
libraryDependencies += "eu.joaocosta" %% "minart-core" % "0.1.2"
```

The `examples` folder contains some sample code.

The recommended way to use Minart is to use the `RenderLoop`,
since it takes care of creating and destroying the window,
while it also takes care of some low-level platform-specific details.

Here's a simple example that renders a color gradient to a screen:

```scala
JavaRenderLoop.singleFrame(new AwtCanvas(128, 128, scale = 4), canvas => {
  for {
    x <- (0 until canvas.width)
    y <- (0 until canvas.height)
  } {
    val color = Color((255 * x.toDouble / canvas.width).toInt, (255 * y.toDouble / canvas.height).toInt, 255)
    canvas.putPixel(x, y, color)
  }
  canvas.redraw()
})
```

Note that you can avoid this and use the `LowLevelCanvas` interface, which is
just a `Canvas with CanvasManager`.

```scala
val canvas = new AwtCanvas(128, 128, scale = 4)
canvas.init()
for {
  x <- (0 until canvas.width)
  y <- (0 until canvas.height)
} {
  val color = Color((255 * x.toDouble / canvas.width).toInt, (255 * y.toDouble / canvas.height).toInt, 255)
  canvas.putPixel(x, y, color)
}
canvas.redraw()

// ... eventually, call `canvas.destroy()`
```

## Multiplatform support

To compile for different platforms, one needs to use the correct `Canvas` and `RenderLoop`:

* JVM: `AwtCanvas` and `JavaRenderLoop`
* JS: `HtmlCanvas` and `JsRenderLoop`
* Native: `SdlCanvas` and `SdlRenderLoop`
