package eu.joaocosta.minart.input

import scala.collection.immutable.Queue

/** The pointer input stores the state of the mouse (or similar device) at a certain point in time.
  * It also accumulates points that have been pressed and released.
  *
  * @param position the current pointer position
  * @param events points where the pointer was pressed/released (the boolean indicates pressed when true, released when false).
  *               Note that only the most recent PointerInput.maxEvents are guaranteed to be present.
  * @param pressedOn if defined, it means the mouse is currently down and was pressed at that position. Otherwise, the mouse is currently up
  */
case class PointerInput(
    position: Option[PointerInput.Position],
    events: Queue[PointerInput.Event],
    pressedOn: Option[PointerInput.Position]
) {

  /** Points where the pointer was pressed */
  lazy val pointsPressed: List[PointerInput.Position] = events.collect { case PointerInput.Event.Pressed(pos) =>
    pos
  }.toList

  /** Points where the pointer was released */
  lazy val pointsReleased: List[PointerInput.Position] = events.collect { case PointerInput.Event.Released(pos) =>
    pos
  }.toList

  /** Check if the mouse button is currently pressed */
  def isPressed: Boolean = pressedOn.isDefined

  /** Updates the pointer position. */
  def move(pos: Option[PointerInput.Position]): PointerInput =
    this.copy(position = pos)

  private def pushEvent(event: PointerInput.Event) = {
    val newQueue = events :+ event
    if (newQueue.size >= PointerInput.maxEvents * 2) this.copy(events = newQueue.drop(PointerInput.maxEvents))
    else this.copy(events = newQueue)
  }

  /** Returns a new state where the pointer has been pressed. */
  def press: PointerInput =
    position
      .map { pos =>
        pushEvent(PointerInput.Event.Pressed(pos)).copy(pressedOn = Some(pos))
      }
      .getOrElse(this)

  /** Returns a new state where the pointer has been released. */
  def release: PointerInput =
    position
      .map { pos =>
        pushEvent(PointerInput.Event.Released(pos)).copy(pressedOn = None)
      }
      .getOrElse(this)

  /** Clears the `pointsPressed` and `pointsReleased`. */
  def clearEvents(): PointerInput = copy(events = Queue.empty)
}

object PointerInput {

  /** Pointer Input with everything unset
    */
  val empty = PointerInput(None, Queue.empty, None)

  /** Maximum events guaranteed to be stored by PointerInput */
  val maxEvents = 1024

  /** Pointer Event */
  sealed trait Event {
    def pos: Position
  }
  object Event {

    /** Event representing a pointer press */
    final case class Pressed(pos: Position) extends Event

    /** Event representing a pointer release */
    final case class Released(pos: Position) extends Event
  }

  /** Position on the screen
    */
  case class Position(x: Int, y: Int)
}
