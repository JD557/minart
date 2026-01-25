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
final case class KeyboardInput(keysDown: Set[Key], events: Queue[KeyboardInput.Event]) {

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
    *
    *  Has a `baseChar` value with an optional char,
    *  which represents the key with no modifiers.
    */
  enum Key(val baseChar: Option[Char]) {
    // Letters
    case A extends Key(Some('a'))
    case B extends Key(Some('b'))
    case C extends Key(Some('c'))
    case D extends Key(Some('d'))
    case E extends Key(Some('e'))
    case F extends Key(Some('f'))
    case G extends Key(Some('g'))
    case H extends Key(Some('h'))
    case I extends Key(Some('i'))
    case J extends Key(Some('j'))
    case K extends Key(Some('k'))
    case L extends Key(Some('l'))
    case M extends Key(Some('m'))
    case N extends Key(Some('n'))
    case O extends Key(Some('o'))
    case P extends Key(Some('p'))
    case Q extends Key(Some('q'))
    case R extends Key(Some('r'))
    case S extends Key(Some('s'))
    case T extends Key(Some('t'))
    case U extends Key(Some('u'))
    case V extends Key(Some('v'))
    case W extends Key(Some('w'))
    case X extends Key(Some('x'))
    case Y extends Key(Some('y'))
    case Z extends Key(Some('z'))

    // Numbers
    case Digit0 extends Key(Some('0'))
    case Digit1 extends Key(Some('1'))
    case Digit2 extends Key(Some('2'))
    case Digit3 extends Key(Some('3'))
    case Digit4 extends Key(Some('4'))
    case Digit5 extends Key(Some('5'))
    case Digit6 extends Key(Some('6'))
    case Digit7 extends Key(Some('7'))
    case Digit8 extends Key(Some('8'))
    case Digit9 extends Key(Some('9'))

    // Numpad Numbers
    case NumPad0 extends Key(Some('0'))
    case NumPad1 extends Key(Some('1'))
    case NumPad2 extends Key(Some('2'))
    case NumPad3 extends Key(Some('3'))
    case NumPad4 extends Key(Some('4'))
    case NumPad5 extends Key(Some('5'))
    case NumPad6 extends Key(Some('6'))
    case NumPad7 extends Key(Some('7'))
    case NumPad8 extends Key(Some('8'))
    case NumPad9 extends Key(Some('9'))

    // Whitespace
    case Space     extends Key(Some(' '))
    case Tab       extends Key(Some('\t'))
    case Enter     extends Key(Some('\r'))
    case Backspace extends Key(Some('\u0008'))

    // Control
    case Escape extends Key(None)
    case Shift  extends Key(None)
    case Ctrl   extends Key(None)
    case Alt    extends Key(None)
    case Meta   extends Key(None)

    // Arrows
    case Up    extends Key(None)
    case Down  extends Key(None)
    case Left  extends Key(None)
    case Right extends Key(None)

    // Punctuation
    case Period          extends Key(Some('.'))
    case Colon           extends Key(Some(':'))
    case Comma           extends Key(Some(','))
    case Semicolon       extends Key(Some(';'))
    case ExclamationMark extends Key(Some('!'))

    // Quotes
    case SingleQuote extends Key(Some('\''))
    case DoubleQuote extends Key(Some('"'))

    // Slashes
    case Slash      extends Key(Some('/'))
    case Backslash  extends Key(Some('\\'))
    case Underscore extends Key(Some('_'))

    // Math
    case Plus     extends Key(Some('+'))
    case Minus    extends Key(Some('-'))
    case Asterisk extends Key(Some('*'))
    case Equals   extends Key(Some('='))

    // Brackets
    case OpenParenthesis  extends Key(Some('('))
    case CloseParenthesis extends Key(Some(')'))

    // Other
    case Ampersand  extends Key(Some('&'))
    case DollarSign extends Key(Some('$'))
    case At         extends Key(Some('@'))
  }
}
