package eu.joaocosta.minart.input

import scala.collection.immutable.Queue

import eu.joaocosta.minart.input.KeyboardInput.Key

/** The keyboard input stores the state of the keyboard at a certain point in time.
  * It also accumulates keys that have been pressed and released.
  *
  * @param keysDown keys that are pressed down
  * @param events ordered press/release keyboard events.
  *               Note that only the most recent `KeyboardInput.maxEvents` are guaranteed to be present.
  */
case class KeyboardInput(keysDown: Set[Key], events: Queue[KeyboardInput.Event]) {

  /** Keys that have been pressed */
  lazy val keysPressed: Set[Key] = events.collect { case KeyboardInput.Event.Pressed(key) =>
    key
  }.toSet

  /** Keys that have been released */
  lazy val keysReleased: Set[Key] = events.collect { case KeyboardInput.Event.Released(key) =>
    key
  }.toSet

  /** Checks if a key is down. */
  def isDown(key: Key): Boolean = keysDown(key)

  /** Checks if a key is up. */
  def isUp(key: Key): Boolean = !keysDown(key)

  private def pushEvent(event: KeyboardInput.Event) = {
    val newQueue = events :+ event
    if (newQueue.size >= KeyboardInput.maxEvents * 2) this.copy(events = newQueue.drop(KeyboardInput.maxEvents))
    else this.copy(events = newQueue)
  }

  /** Returns a new state where a key has been pressed. */
  def press(key: Key): KeyboardInput =
    if (keysDown(key)) this // Ignore key repeat
    else pushEvent(KeyboardInput.Event.Pressed(key)).copy(keysDown = keysDown + key)

  /** Returns a new state where a key has been released. */
  def release(key: Key): KeyboardInput =
    pushEvent(KeyboardInput.Event.Released(key)).copy(keysDown = keysDown - key)

  /** Clears the `keysPressed` and `keysReleased`. */
  def clearPressRelease(): KeyboardInput = KeyboardInput(keysDown, Queue.empty)
}

object KeyboardInput {

  /** Keyboard Input with everything unset
    */
  val empty = KeyboardInput(Set(), Queue.empty)

  /** Maximum events guaranteed to be stored by KeyboardInput */
  val maxEvents = 1024

  /** Keyboard Event */
  sealed trait Event {
    def key: Key
  }
  object Event {

    /** Event representing a key press */
    final case class Pressed(key: Key) extends Event

    /** Event representing a key release */
    final case class Released(key: Key) extends Event
  }

  /** Internal trait to store mappings from platform-specific key representations to a [[Key]].
    * This should not have to be used in the application code.
    */
  trait KeyMapping[A] {
    protected def mappings: Map[A, Key]
    def getKey(keyCode: A): Option[Key] = mappings.get(keyCode)
  }

  /** Platform-agnostic identifier for a keyboard key.
    */
  enum Key {
    case A
    case B
    case C
    case D
    case E
    case F
    case G
    case H
    case I
    case J
    case K
    case L
    case M
    case N
    case O
    case P
    case Q
    case R
    case S
    case T
    case U
    case V
    case W
    case X
    case Y
    case Z

    case Digit0
    case Digit1
    case Digit2
    case Digit3
    case Digit4
    case Digit5
    case Digit6
    case Digit7
    case Digit8
    case Digit9

    case NumPad0
    case NumPad1
    case NumPad2
    case NumPad3
    case NumPad4
    case NumPad5
    case NumPad6
    case NumPad7
    case NumPad8
    case NumPad9

    case Space
    case Tab
    case Enter
    case Backspace

    case Escape
    case Shift
    case Ctrl
    case Alt
    case Meta

    case Up
    case Down
    case Left
    case Right
  }
}
