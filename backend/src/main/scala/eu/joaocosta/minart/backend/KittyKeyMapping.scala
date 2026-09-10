package eu.joaocosta.minart.backend

import eu.joaocosta.minart.input.KeyboardInput.*

/** Key mappings for Kitty
  */
object KittyKeyMapping extends KeyMapping[Char] {
  protected val mappings: Map[Char, Key] = Map(
    // Letters
    'a' -> Key.A,
    'b' -> Key.B,
    'c' -> Key.C,
    'd' -> Key.D,
    'e' -> Key.E,
    'f' -> Key.F,
    'g' -> Key.G,
    'h' -> Key.H,
    'i' -> Key.I,
    'j' -> Key.J,
    'k' -> Key.K,
    'l' -> Key.L,
    'm' -> Key.M,
    'n' -> Key.N,
    'o' -> Key.O,
    'p' -> Key.P,
    'q' -> Key.Q,
    'r' -> Key.R,
    's' -> Key.S,
    't' -> Key.T,
    'u' -> Key.U,
    'v' -> Key.V,
    'w' -> Key.W,
    'x' -> Key.X,
    'y' -> Key.Y,
    'z' -> Key.Z,
    // Numbers
    '0' -> Key.Digit0,
    '1' -> Key.Digit1,
    '2' -> Key.Digit2,
    '3' -> Key.Digit3,
    '4' -> Key.Digit4,
    '5' -> Key.Digit5,
    '6' -> Key.Digit6,
    '7' -> Key.Digit7,
    '8' -> Key.Digit8,
    '9' -> Key.Digit9,
    // Numpad Numbers
    // TODO
    // Whitespace
    // TODO
    ' ' -> Key.Space,
    // Accents
    '`' -> Key.Backtick,
    '^' -> Key.Caret,
    '~' -> Key.Tilde,
    // Control
    // TODO
    // Arrows
    // TODO
    // Punctuation
    '.' -> Key.Period,
    ':' -> Key.Colon,
    ',' -> Key.Comma,
    ';' -> Key.Semicolon,
    '!' -> Key.ExclamationMark,
    '?' -> Key.QuestionMark,
    // Quotes
    '\'' -> Key.SingleQuote,
    '"'  -> Key.DoubleQuote,
    // Slashes
    '/'  -> Key.Slash,
    '\\' -> Key.Backslash,
    '_'  -> Key.Underscore,
    '|'  -> Key.Pipe,
    // Math
    '+' -> Key.Plus,
    '-' -> Key.Minus,
    '*' -> Key.Asterisk,
    '=' -> Key.Equals,
    '%' -> Key.Percentage,
    // Brackets
    '(' -> Key.OpenParenthesis,
    ')' -> Key.CloseParenthesis,
    '[' -> Key.OpenBracket,
    ']' -> Key.CloseBracket,
    // Other
    '&' -> Key.Ampersand,
    '$' -> Key.DollarSign,
    '@' -> Key.At,
    '#' -> Key.Hash
  )
}
