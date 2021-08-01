package eu.joaocosta.minart.core

/** The pointer input stores the state of the mouse (or similar device) at a certain point in time.
  * It also accumulates points that have been pressed and released.
  *
  * @param position the current pointer position
  * @param pointsPressed points where the pointer pressed
  * @param keysReleased points where the pointer was released
  * @param isPressed true if the pointer is currently pressed
  */
case class PointerInput(
    position: Option[PointerInput.Position],
    pointsPressed: List[PointerInput.Position],
    pointsReleased: List[PointerInput.Position],
    isPressed: Boolean
) {

  /** Updates the pointer position. */
  def move(pos: Option[PointerInput.Position]): PointerInput =
    this.copy(position = pos)

  /** Returns a new state where the pointer has been pressed. */
  def press: PointerInput =
    position
      .map { pos =>
        this.copy(pointsPressed = pointsPressed :+ pos, isPressed = true)
      }
      .getOrElse(this)

  /** Returns a new state where the pointer has been released. */
  def release: PointerInput =
    position
      .map { pos =>
        this.copy(pointsReleased = pointsReleased :+ pos, isPressed = false)
      }
      .getOrElse(this)

  /** Clears the `pointsPressed` and `pointsReleased`. */
  def clearPressRelease(): PointerInput =
    PointerInput(position, if (isPressed) pointsPressed.lastOption.toList else Nil, Nil, isPressed)
}

object PointerInput {
  case class Position(x: Int, y: Int)
}
