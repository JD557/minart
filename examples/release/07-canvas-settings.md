# 7. Canvas settings

Up until now, we've been declaring our canvas settings as a constant.

However, it can be sometimes desirable to change the settings at runtime, for example, to enter and leave fullscreen.

Here's a quick example on how to do that. In this example application, we will change the settings when the user presses "A", "B", "C" or "D".

## Variable settings

### Dependencies and imports

```scala
//> using scala "3.3.6"
//> using dep "eu.joaocosta::minart::0.6.4"

import eu.joaocosta.minart.backend.defaults.given
import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.input.*
import eu.joaocosta.minart.runtime.*
```

### Settings

Let's define some settings.
Some of those settings use a different resolution, while others go full screen.

```scala
val settingsA = Canvas.Settings(width = 128, height = 128, scale = Some(4), clearColor = Color(128, 255, 128))
val settingsB = Canvas.Settings(width = 640, height = 480, scale = None, clearColor = Color(128, 255, 128))
val settingsC =
  Canvas.Settings(width = 128, height = 128, scale = Some(4), fullScreen = true, clearColor = Color(128, 255, 128))
val settingsD = Canvas.Settings(width = 640, height = 480, scale = None, fullScreen = true, clearColor = Color(0, 0, 0))
```

### Changing settings

The application logic is pretty simple: We grab the keyboard input and, based on the key that is pressed, we call `canvas.changeSettings` with the desired settings.

```scala
def changeSettings(canvas: Canvas, keyboardInput: KeyboardInput): Unit = {
  if (keyboardInput.keysPressed(KeyboardInput.Key.A)) canvas.changeSettings(settingsA)
  else if (keyboardInput.keysPressed(KeyboardInput.Key.B)) canvas.changeSettings(settingsB)
  else if (keyboardInput.keysPressed(KeyboardInput.Key.C)) canvas.changeSettings(settingsC)
  else if (keyboardInput.keysPressed(KeyboardInput.Key.D)) canvas.changeSettings(settingsD)
}
```

### Reading settings

Since our settings will change, we also need to make sure we read the updated values. Those can be read by using `Canvas#canvasSettings`.

In this example, we'll draw a colored gradient that fills the screen, so we need to fetch the current width and height of the canvas. Since this is so common, we can actually just use the `Canvas#width` and `Canvas#height` methods.

```scala
def drawColors(canvas: Canvas): Unit = {
  for {
    x <- (0 until canvas.width)
    y <- (0 until canvas.height)
    color = Color((255 * x.toDouble / canvas.width).toInt, (255 * y.toDouble / canvas.height).toInt, 255)
  } canvas.putPixel(x, y, color)
}
```

### Putting it all together

```scala
AppLoop
  .statelessRenderLoop((canvas: Canvas) => {
    val keyboardInput = canvas.getKeyboardInput()
    canvas.clear()
    changeSettings(canvas, keyboardInput)
    drawColors(canvas)
    canvas.redraw()
  })
  .configure(
    settingsA,
    LoopFrequency.Uncapped
  )
  .run()
```
