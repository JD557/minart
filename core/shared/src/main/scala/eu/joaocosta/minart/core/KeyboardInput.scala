package eu.joaocosta.minart.core

import eu.joaocosta.minart.core.KeyboardInput.Key

case class KeyboardInput(keysDown: Set[Key], keysPressed: Set[Key], keysReleased: Set[Key]) {
  def isDown(key: Key): Boolean = keysDown(key)
  def isUp(key: Key): Boolean = !keysDown(key)
  def press(key: Key): KeyboardInput = KeyboardInput(keysDown + key, keysPressed + key, keysReleased - key)
  def release(key: Key): KeyboardInput = KeyboardInput(keysDown - key, keysPressed - key, keysReleased + key)
  def clearPressRelease(): KeyboardInput = KeyboardInput(keysDown, Set(), Set())
}

object KeyboardInput {

  trait KeyMapping[A] {
    protected def mappings: Map[A, Key]
    def getKey(keyCode: A): Option[Key] = mappings.get(keyCode)
  }

  sealed trait Key
  object Key {
    final case object A extends Key
    final case object B extends Key
    final case object C extends Key
    final case object D extends Key
    final case object E extends Key
    final case object F extends Key
    final case object G extends Key
    final case object H extends Key
    final case object I extends Key
    final case object J extends Key
    final case object K extends Key
    final case object L extends Key
    final case object M extends Key
    final case object N extends Key
    final case object O extends Key
    final case object P extends Key
    final case object Q extends Key
    final case object R extends Key
    final case object S extends Key
    final case object T extends Key
    final case object U extends Key
    final case object V extends Key
    final case object W extends Key
    final case object X extends Key
    final case object Y extends Key
    final case object Z extends Key

    final case object Digit0 extends Key
    final case object Digit1 extends Key
    final case object Digit2 extends Key
    final case object Digit3 extends Key
    final case object Digit4 extends Key
    final case object Digit5 extends Key
    final case object Digit6 extends Key
    final case object Digit7 extends Key
    final case object Digit8 extends Key
    final case object Digit9 extends Key

    final case object NumPad0 extends Key
    final case object NumPad1 extends Key
    final case object NumPad2 extends Key
    final case object NumPad3 extends Key
    final case object NumPad4 extends Key
    final case object NumPad5 extends Key
    final case object NumPad6 extends Key
    final case object NumPad7 extends Key
    final case object NumPad8 extends Key
    final case object NumPad9 extends Key

    final case object Space extends Key
    final case object Tab extends Key
    final case object Enter extends Key
    final case object Backspace extends Key

    final case object Shift extends Key
    final case object Ctrl extends Key
    final case object Alt extends Key
    final case object Meta extends Key

    final case object Up extends Key
    final case object Down extends Key
    final case object Left extends Key
    final case object Right extends Key
  }
}

