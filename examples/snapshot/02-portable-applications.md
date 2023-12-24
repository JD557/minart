# 2. Writing portable applications

The first example only ran on the JVM, but Minart provides some abstractions to easily cross compile to JS and Native backends.

In this next example, we are going to do draw the same colored square, but now in a portable way.

## Running the examples

As in the previous example, you can run this file with Scala CLI:

```
scala-cli 02-portable-applications.md
```

Although now you can also run it with other targets. For example, to run a native version, just use the following (requires SDL):

```
scala-cli --native --native-mode release 02-portable-application.md
```

It is recommended to use either `release` or `release-fast` for native applications, as the performance in `debug` will make most applications unusable.

If you want to compile to JS, just package the application instead with:

```
scala-cli --power package --js --js-mode release -f 02-portable-applications.md
```

And include the resulting artifact in an HTML file.

## Another simple colored square

### Dependencies and imports

We start with the similar imports to the previous example.

Note, however, that we now also import `eu.joaocosta.minart.runtime.*`, which provides a platform agnostic runtime that we can use.

```scala
//> using scala "3.3.1"
//> using lib "eu.joaocosta::minart::0.6.0-SNAPSHOT"

import eu.joaocosta.minart.backend.defaults.given
import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.runtime.*
```

### Application code

The application logic is pretty much the same.

We will just extract it to a function for reasons that will be clearer in the next step.

```scala
def application(canvas: Canvas): Unit = {
  for {
    x <- (0 until canvas.width)
    y <- (0 until canvas.height)
  } {
    val color =
      Color((255 * x.toDouble / canvas.width).toInt, (255 * y.toDouble / canvas.height).toInt, 255)
    canvas.putPixel(x, y, color)
  }
  canvas.redraw()
}
```

### Application loop

Now, here's the different bit.

One of the main problems of writing cross-platform code is writing a render loop.

Some platforms don't support thread sleep, or require some event loop to be polled.

As such, Minart comes with some helpful `AppLoop` abstractions, which handle canvas creation, destruction and the render loop itself.

Here we will use the `AppState.statelessRenderLoop` operation with `LoopFrequency.Never`, which will show a single image and then wait for the window to be closed.

```scala
val canvasSettings = Canvas.Settings(width = 128, height = 128, scale = Some(4))

AppLoop
  .statelessRenderLoop(application)
  .configure(canvasSettings, LoopFrequency.Never)
  .run()
```
