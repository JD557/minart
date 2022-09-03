package eu.joaocosta.minart.input

import eu.joaocosta.minart.input.KeyboardInput.Key

/** The keyboard input stores the state of the keyboard at a certain point in time.
  * It also accumulates keys that have been pressed and released.
  *
  * @param keysDown keys that are pressed down
  * @param keysPressed keys that have been pressed
  * @param keysReleased keys that have been released
  */
case class KeyboardInput(keysDown: Set[Key], keysPressed: Set[Key], keysReleased: Set[Key]) {

  /** Checks if a key is down. */
  def isDown(key: Key): Boolean = keysDown(key)

  /** Checks if a key is up. */
  def isUp(key: Key): Boolean = !keysDown(key)

  /** Returns a new state where a key has been pressed. */
  def press(key: Key): KeyboardInput =
    if (keysDown(key)) this
    else KeyboardInput(keysDown + key, keysPressed + key, keysReleased - key)

  /** Returns a new state where a key has been released. */
  def release(key: Key): KeyboardInput = KeyboardInput(keysDown - key, keysPressed - key, keysReleased + key)

  /** Clears the `keysPressed` and `keysReleased`. */
  def clearPressRelease(): KeyboardInput = KeyboardInput(keysDown, Set(), Set())
}

object KeyboardInput {

  /** Keyboard Input with everything unset
    */
  val empty = KeyboardInput(Set(), Set(), Set())

  /** Internal trait to store mappings from platform-specific key representations to a [[Key]].
    * This should not have to be used in the application code.
    */
  trait KeyMapping[A] {
    protected def mappings: Map[A, Key]
    def getKey(keyCode: A): Option[Key] = mappings.get(keyCode)
  }

  /** Platform-agnostic identifier for a keyboard key.
    */
  sealed trait Key
  object Key {
    case object A extends Key
    case object B extends Key
    case object C extends Key
    case object D extends Key
    case object E extends Key
    case object F extends Key
    case object G extends Key
    case object H extends Key
    case object I extends Key
    case object J extends Key
    case object K extends Key
    case object L extends Key
    case object M extends Key
    case object N extends Key
    case object O extends Key
    case object P extends Key
    case object Q extends Key
    case object R extends Key
    case object S extends Key
    case object T extends Key
    case object U extends Key
    case object V extends Key
    case object W extends Key
    case object X extends Key
    case object Y extends Key
    case object Z extends Key

    case object Digit0 extends Key
    case object Digit1 extends Key
    case object Digit2 extends Key
    case object Digit3 extends Key
    case object Digit4 extends Key
    case object Digit5 extends Key
    case object Digit6 extends Key
    case object Digit7 extends Key
    case object Digit8 extends Key
    case object Digit9 extends Key

    case object NumPad0 extends Key
    case object NumPad1 extends Key
    case object NumPad2 extends Key
    case object NumPad3 extends Key
    case object NumPad4 extends Key
    case object NumPad5 extends Key
    case object NumPad6 extends Key
    case object NumPad7 extends Key
    case object NumPad8 extends Key
    case object NumPad9 extends Key

    case object Space     extends Key
    case object Tab       extends Key
    case object Enter     extends Key
    case object Backspace extends Key

    case object Escape extends Key
    case object Shift  extends Key
    case object Ctrl   extends Key
    case object Alt    extends Key
    case object Meta   extends Key

    case object Up    extends Key
    case object Down  extends Key
    case object Left  extends Key
    case object Right extends Key
  }
}
