# 10. Vector shapes

Besides surfaces, Minart also allows you to render some basic vector shapes.

## Drawing shapes

### Dependencies and imports

The relevant methods are in the `eu.joaocosta.minart.geometry` package

```scala
//> using scala "3.3.7"
//> using dep "eu.joaocosta::minart::0.6.5"

import eu.joaocosta.minart.backend.defaults.given
import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.geometry.*
import eu.joaocosta.minart.runtime.*
```

### Shapes

Vectorial shapes are represented by the `Shape` abstraction.

Minart already comes with some basic shapes, such as circle and convex polygons, with helper methods in the `Shape` companion object.

First, let's create a few shapes with those methods.

```scala
import eu.joaocosta.minart.geometry.Point

val triangle = Shape.triangle(Point(-16, 16), Point(0, -16), Point(16, 16))
val square = Shape.rectangle(Point(-16, -16), Point(16, 16))
val octagon = Shape.convexPolygon(
    Point(-8, -16),
    Point(8, -16),
    Point(16, -8),
    Point(16, 8),
    Point(8, 16),
    Point(-8, 16),
    Point(-16, 8),
    Point(-16, -8)
)
val circle = Shape.circle(Point(0, 0), 16)
```

### Faces

Notice that all shapes are defined with points in clockwise fashion.

All shapes have two faces: A front face and a back face.

Depending on the way they are defined (or transformed), different faces might be shown.

Minart allows you to set different colors for each face, and even no color at all!
This is helpful if, for some reason, you know you don't want to draw back faces.

### Rasterizing

Now we just need to use the `rasterizeShape` operation, just like we did with `blit`.

In this example we will also scale our images with time, to show how the color changes when the face flips.

There's also a `rasterizeStroke` operation to draw lines and a `rasterizeContour` operation to draw only the shape
contours (note that not all shapes support this, some transformations can make the contour computation impossible).

```scala
val frontfaceColor = Color(255, 0, 0)
val backfaceColor = Color(0, 255, 0)
val contourColor = Color(255, 255, 255)

def application(t: Double, canvas: Canvas): Unit = {
  val scale = math.sin(t)
  canvas.rasterizeShape(triangle.scale(scale, 1.0), Some(frontfaceColor), Some(backfaceColor))(32, 32)
  canvas.rasterizeShape(square.scale(scale, 1.0), Some(frontfaceColor), Some(backfaceColor))(64, 32)
  canvas.rasterizeShape(octagon.scale(scale, 1.0), Some(frontfaceColor), Some(backfaceColor))(32, 64)
  canvas.rasterizeShape(circle.scale(scale, 1.0), Some(frontfaceColor), Some(backfaceColor))(64, 64)

  canvas.rasterizeContour(triangle.scale(scale, 1.0), contourColor)(32, 32)
  canvas.rasterizeContour(square.scale(scale, 1.0), contourColor)(64, 32)
  canvas.rasterizeContour(octagon.scale(scale, 1.0), contourColor)(32, 64)
  // Can't compute the contour of a circle scaled on only one dimension
  //canvas.rasterizeContour(circle.scale(scale, 1.0), contourColor)(64, 64)
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
