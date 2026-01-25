package eu.joaocosta.minart.input

import eu.joaocosta.minart.input.KeyboardInput.Key

final case class KeyboardLayout(shiftModifiers: Map[Key, Key]) {
  def shift(k: Key): Key =
    shiftModifiers.getOrElse(k, k)
}

object KeyboardLayout {
  final val us = KeyboardLayout(
    shiftModifiers = Map(
      // Row 0
      Key.Backtick -> Key.Tilde,
      Key.Digit1   -> Key.ExclamationMark,
      Key.Digit2   -> Key.At,
      Key.Digit3   -> Key.Hash,
      Key.Digit4   -> Key.DollarSign,
      Key.Digit5   -> Key.Percentage,
      Key.Digit6   -> Key.Caret,
      Key.Digit7   -> Key.Ampersand,
      Key.Digit8   -> Key.Asterisk,
      Key.Digit9   -> Key.OpenParenthesis,
      Key.Digit0   -> Key.CloseParenthesis,
      Key.Minus    -> Key.Underscore,
      Key.Equals   -> Key.Plus,
      // Row 1
      // Row 2
      Key.Semicolon   -> Key.Colon,
      Key.SingleQuote -> Key.DoubleQuote,
      Key.Backslash   -> Key.Pipe,
      // Row 3
      Key.Slash -> Key.QuestionMark
    )
  )

  final val uk = KeyboardLayout(
    shiftModifiers = Map(
      // Row 0
      Key.Digit1 -> Key.ExclamationMark,
      Key.Digit2 -> Key.DoubleQuote,
      // Key.Digit3 -> Key.PountSign,
      Key.Digit4 -> Key.DollarSign,
      Key.Digit5 -> Key.Percentage,
      Key.Digit6 -> Key.Caret,
      Key.Digit7 -> Key.Ampersand,
      Key.Digit8 -> Key.Asterisk,
      Key.Digit9 -> Key.OpenParenthesis,
      Key.Digit0 -> Key.CloseParenthesis,
      Key.Minus  -> Key.Underscore,
      Key.Equals -> Key.Plus,
      // Row 1
      // Row 2
      Key.Semicolon   -> Key.Colon,
      Key.SingleQuote -> Key.At,
      Key.Hash        -> Key.Tilde,
      // Row 3
      Key.Backslash -> Key.Pipe,
      Key.Slash     -> Key.QuestionMark
    )
  )

  final val es = KeyboardLayout(
    shiftModifiers = Map(
      // Row 0
      Key.Digit1 -> Key.ExclamationMark,
      Key.Digit2 -> Key.DoubleQuote,
      // Key.Digit3 -> Key.Dot,
      Key.Digit4      -> Key.DollarSign,
      Key.Digit5      -> Key.Percentage,
      Key.Digit6      -> Key.Ampersand,
      Key.Digit7      -> Key.Slash,
      Key.Digit9      -> Key.OpenParenthesis,
      Key.Digit9      -> Key.CloseParenthesis,
      Key.Digit0      -> Key.Equals,
      Key.SingleQuote -> Key.QuestionMark,
      // Row 1
      Key.Backtick -> Key.Caret,
      Key.Plus     -> Key.Asterisk,
      // Row 2
      // Row 3
      Key.Period -> Key.Colon,
      Key.Comma  -> Key.Semicolon,
      Key.Minus  -> Key.Underscore
    )
  )

  final val pt = KeyboardLayout(
    shiftModifiers = Map(
      // Row 0
      Key.Digit1      -> Key.ExclamationMark,
      Key.Digit2      -> Key.DoubleQuote,
      Key.Digit3      -> Key.Hash,
      Key.Digit4      -> Key.DollarSign,
      Key.Digit5      -> Key.Percentage,
      Key.Digit6      -> Key.Ampersand,
      Key.Digit7      -> Key.Slash,
      Key.Digit9      -> Key.OpenParenthesis,
      Key.Digit9      -> Key.CloseParenthesis,
      Key.Digit0      -> Key.Equals,
      Key.SingleQuote -> Key.QuestionMark,
      // Row 1
      Key.Plus -> Key.Asterisk,
      // Row 2
      Key.Tilde -> Key.Caret,
      // Row 3
      Key.Period -> Key.Colon,
      Key.Comma  -> Key.Semicolon,
      Key.Minus  -> Key.Underscore
    )
  )
}
