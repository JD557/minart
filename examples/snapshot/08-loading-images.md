# 8. Loading images

So far we always created our surfaces manually. This is fine for small demos and procedural graphics, but often it can be useful to load an image from an external file.

Minart has support for some image file formats (PBM/PPM, PDI, BMP and QOI). Here we will see how to load them.

## A simple image viewer

### Dependencies and imports

The image loaders are included in the `graphics.image` package, so we need to include that.

This package also has an `Image` object with helpers to call the loaders.


```scala
//> using scala "3.3.6"
//> using dep "eu.joaocosta::minart::0.6.5-SNAPSHOT"

import eu.joaocosta.minart.backend.defaults.given
import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.graphics.image.*
import eu.joaocosta.minart.runtime.*
```

### Loading an image

To load an image in the BMP format, we simply use the `Image.loadBmpImage` method.

```scala
val bitmap = Image.loadBmpImage(Resource("assets/scala.bmp")).get
```

Notice the use of `Resource` here.

The `Resource` primitive is part of the `runtime` package, and it abstracts file access in a platform agnostic way.

It can access files from the file system, packed resources or from an URL, depending on the backend.

Also, notice that `Image.loadBmpImage` returns a `Try[RamSurface]`. In this example we skip the error handling and simply call `.get`, but on a real application you might want to do something if loading the image fails.

After getting the `Surface`, we can just draw the image in the canvas.

```scala
val canvasSettings = Canvas.Settings(width = 128, height = 128, scale = Some(4))

AppLoop
  .statelessRenderLoop((canvas: Canvas) => {
    canvas.blit(bitmap)(0, 0)
    canvas.redraw()
  })
  .configure(canvasSettings, LoopFrequency.Never)
  .run()
```
