package eu.joaocosta.minart.input

class PointerInputSpec extends munit.FunSuite {

  test("Correctly captures pointer presses and releases") {
    val input = PointerInput.empty

    val input1 = input.move(Some(PointerInput.Position(5, 5))).press
    assert(input1.pressedOn == Some(PointerInput.Position(5, 5)))

    val input2 = input1.release
    assert(input2.pressedOn == None)
  }
}
