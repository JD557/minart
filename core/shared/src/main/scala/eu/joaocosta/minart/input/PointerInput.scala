package eu.joaocosta.minart.input

/** The pointer input stores the state of the mouse (or similar device) at a certain point in time.
  * It also accumulates points that have been pressed and released.
  *
  * @param position the current pointer position
  * @param events points where the pointer was pressed/released (the boolean indicates pressed when true, released when false)
  * @param keysReleased points where the pointer was released
  * @param pressedOn if defined, it means the mouse is currently down and was pressed at that position. Otherwise, the mouse is currently up
  */
case class PointerInput(
    position: Option[PointerInput.Position],
    events: List[(PointerInput.Position, Boolean)],
    pressedOn: Option[PointerInput.Position]
) {

  /* Points where the pointer was pressed */
  lazy val pointsPressed: List[PointerInput.Position] = events.filter(_._2).map(_._1)

  /* Points where the pointer was released */
  lazy val pointsReleased: List[PointerInput.Position] = events.filterNot(_._2).map(_._1)

  /** Check if the mouse button is currently pressed */
  def isPressed: Boolean = pressedOn.isDefined

  /** Updates the pointer position. */
  def move(pos: Option[PointerInput.Position]): PointerInput =
    this.copy(position = pos)

  /** Returns a new state where the pointer has been pressed. */
  def press: PointerInput =
    position
      .map { pos =>
        this.copy(events = events :+ (pos, true), pressedOn = Some(pos))
      }
      .getOrElse(this)

  /** Returns a new state where the pointer has been released. */
  def release: PointerInput =
    position
      .map { pos =>
        this.copy(events = events :+ (pos, false), pressedOn = None)
      }
      .getOrElse(this)

  /** Clears the `pointsPressed` and `pointsReleased`. */
  def clearEvents(): PointerInput = copy(events = Nil)
}

object PointerInput {

  /** Pointer Input with everything unset
    */
  val empty = PointerInput(None, Nil, None)

  /** Position on the screen
    */
  case class Position(x: Int, y: Int)
}
