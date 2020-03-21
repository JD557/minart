package eu.joaocosta.minart

import eu.joaocosta.minart.KeyboardInput.Key

case class KeyboardInput(keysDown: Set[Key], keysPressed: Set[Key], keysReleased: Set[Key]) {
  def isDown(key: Key): Boolean = keysDown(key)
  def isUp(key: Key): Boolean = !keysDown(key)
  def press(key: Key): KeyboardInput = KeyboardInput(keysDown + key, keysPressed + key, keysReleased - key)
  def release(key: Key): KeyboardInput = KeyboardInput(keysDown - key, keysPressed - key, keysReleased + key)
  def clearPressRelease(): KeyboardInput = KeyboardInput(keysDown, Set(), Set())
}

object KeyboardInput {
  sealed trait Key
  object Key {
    case object Up extends Key
    case object Down extends Key
    case object Left extends Key
    case object Right extends Key
  }
}

