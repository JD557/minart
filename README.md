# Minart

![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/eu.joaocosta/minart-core_2.13?server=https%3A%2F%2Foss.sonatype.org)
 [![scaladoc (core)](https://javadoc.io/badge2/eu.joaocosta/minart-core_2.13/scaladoc%20%28core%29.svg)](https://javadoc.io/doc/eu.joaocosta/minart-core_2.13)
 [![scaladoc (pure)](https://javadoc.io/badge2/eu.joaocosta/minart-pure_2.13/scaladoc%20%28pure%29.svg)](https://javadoc.io/doc/eu.joaocosta/minart-pure_2.13)

Minart is a very minimalistic Scala library to put pixels in a canvas.

It's mostly useful for small toy projects or prototypes that deal with
generative art or software rendering.

Minart is still in a 0.x version. Quoting the semver specification:
> Major version zero (0.y.z) is for initial development. Anything MAY change at any time. The public API SHOULD NOT be considered stable.

## Features

* JVM, JS and Native support
* Small footprint
* Double buffered canvas
* Integer scaling
* Keyboard and pointer input
* Surface blitting (with a mask)

## Getting Started

### The Canvas

The `Canvas`, which provides the operations required to draw on the window and read inputs:

#### Rendering Operations

* `getPixel(x: Int, y: Int): Option[Color]`
* `getPixels(): Vector[Array[Color]]`
* `putPixel(x: Int, y: Int, color: Color): Unit`
* `fill(color: Color): Unit`
* And more...

#### Input operations

* `getKeyboardInput(): KeyboardInput`
* `getPointerInput(): PointerInput`

#### Render Loop operations

* `clear(resources: Set[Canvas.Resource]): Unit`
* `redraw(): Unit`

### Settings

* `width: Int`
* `height: Int`
* `settings: Canvas.Settings`
* `changeSettings(newSettings: Canvas.Settings): Unit`

### Creating a Canvas

The simplest way to create a `Canvas` is to simply create a default canvas with
`Canvas.create(settings: Canvas.Settings)`
(e.g. `Canvas.create(Canvas.Settings(width = 640, height = 480))`).

This will immediately create a window that can manipulated with the canvas operations.

In practice, it's sometimes cumbersome to have the constructor perform side-effects like
creating the window, so it is recommended to use the default `CanvasManager` instead, with
`CanvasManager()`.

The `CanvasManager` has a single `init(settings: Canvas.Settings)` method, that creates
and returns a `LowLevelCanvas` (a `Canvas` with an extra `close` operation).

### The RenderLoop

While Minart provides `Canvas` implementations for the JVM, JS and Native, writing
cross compatible render loops is not trivial
(JavaScript render loops work with callbacks, native render loops with SDL need to
read the SDL events to see if the window was closed...).

To tackle this problem, the `RenderLoop` abstraction provides helpful operations
to handle the render loops.

It also takes care of creating and destroying the window.

Note that `clear`/`redraw` still need to be called manually (since in some cases, it might be useful to not call those methods, especially `clear`).

The render loop definition might seem a bit complicated (since it attempts to be agnostic of the effect type. There's an `ImpureRenderLoop` implementation that simply uses side-effectful functions.

* `singleFrame(renderFrame: Canvas => Unit)(canvasSettings: Canvas.Settings): Unit`
* `infiniteRenderLoop(renderFrame: Canvas => Unit, frameRate: FrameRate)(canvasSettings: Canvas.Settings): Unit`
* `infiniteRenderLoop[S](renderFrame: (Canvas, S) => S, frameRate: FrameRate)(canvas.Settings, initialState: S): Unit`
* `finiteRenderLoop[S](renderFrame: (Canvas, S) => S, terminateWhen: S => Boolean, frameRate: FrameRate)(canvas.Settings, initialState: S): Unit`

## Sample Usage

To include Minart, simply add `minart-core` to your project:

```scala
libraryDependencies += "eu.joaocosta" %% "minart-core" % "0.3.0-RC1"
```

Or just create a new project using the provided giter8 template, with:

```
g8 https://github.com/JD557/minart
```

The `examples` folder contains some sample code.

Here's a simple example that renders a color gradient to a screen:

```scala
import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.graphics._

object ColorSquare {
  val canvasSettings = Canvas.Settings(width = 128, height = 128, scale = 4)

  def main(args: Array[String]): Unit = {
    ImpureRenderLoop
      .singleFrame(c => {
        for {
          x <- (0 until c.width)
          y <- (0 until c.height)
        } {
          val color =
            Color((255 * x.toDouble / c.width).toInt, (255 * y.toDouble / c.height).toInt, 255)
          c.putPixel(x, y, color)
        }
        c.redraw()
      })(canvasSettings)
  }
}
```

## Multiplatform support

To compile for different platforms, one needs to either use import `eu.joaocosta.minart.backend.defaults._`
package and use the `apply` method or use the correct `Canvas` and `RenderLoop`.

The following canvas and render loops are available:
* All: `PpmCanvas`
* JVM: `AwtCanvas` and `JavaRenderLoop`
* JS: `HtmlCanvas` and `JsRenderLoop`
* Native: `SdlCanvas` and `SdlRenderLoop`
