package eu.joaocosta.minart.input

import verify._

object KeyboardInputSpec extends BasicTestSuite {

  test("Correctly captures key presses and releases") {
    val input = KeyboardInput.empty

    val input1 = input.press(KeyboardInput.Key.A)
    assert(input1.keysDown == Set(KeyboardInput.Key.A))
    assert(input1.keysPressed == Set(KeyboardInput.Key.A))
    assert(input1.keysReleased == Set())

    val input2 = input1.press(KeyboardInput.Key.B)
    assert(input2.keysDown == Set(KeyboardInput.Key.A, KeyboardInput.Key.B))
    assert(input2.keysPressed == Set(KeyboardInput.Key.A, KeyboardInput.Key.B))
    assert(input2.keysReleased == Set())

    val input3 = input2.release(KeyboardInput.Key.A)
    assert(input3.keysDown == Set(KeyboardInput.Key.B))
    assert(input3.keysPressed == Set(KeyboardInput.Key.A, KeyboardInput.Key.B))
    assert(input3.keysReleased == Set(KeyboardInput.Key.A))

    val input4 = input3.clearPressRelease()
    assert(input4.keysDown == Set(KeyboardInput.Key.B))
    assert(input4.keysPressed == Set())
    assert(input4.keysReleased == Set())
  }
}
