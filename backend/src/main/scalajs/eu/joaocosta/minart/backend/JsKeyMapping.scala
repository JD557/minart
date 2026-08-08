package eu.joaocosta.minart.backend

import eu.joaocosta.minart.input.KeyboardInput.*

/** Key mappings for the JavaScript backend.
  */
object JsKeyMapping extends KeyMapping[Int] {
  protected val mappings: Map[Int, Key] = Map(
    // Letters
    65 -> Key.A,
    66 -> Key.B,
    67 -> Key.C,
    68 -> Key.D,
    69 -> Key.E,
    70 -> Key.F,
    71 -> Key.G,
    72 -> Key.H,
    73 -> Key.I,
    74 -> Key.J,
    75 -> Key.K,
    76 -> Key.L,
    77 -> Key.M,
    78 -> Key.N,
    79 -> Key.O,
    80 -> Key.P,
    81 -> Key.Q,
    82 -> Key.R,
    83 -> Key.S,
    84 -> Key.T,
    85 -> Key.U,
    86 -> Key.V,
    87 -> Key.W,
    88 -> Key.X,
    89 -> Key.Y,
    90 -> Key.Z,
    // Numbers
    48 -> Key.Digit0,
    49 -> Key.Digit1,
    50 -> Key.Digit2,
    51 -> Key.Digit3,
    52 -> Key.Digit4,
    53 -> Key.Digit5,
    54 -> Key.Digit6,
    55 -> Key.Digit7,
    56 -> Key.Digit8,
    57 -> Key.Digit9,
    // Numpad Numbers
    96  -> Key.NumPad0,
    97  -> Key.NumPad1,
    98  -> Key.NumPad2,
    99  -> Key.NumPad3,
    100 -> Key.NumPad4,
    101 -> Key.NumPad5,
    102 -> Key.NumPad6,
    103 -> Key.NumPad7,
    104 -> Key.NumPad8,
    105 -> Key.NumPad9,
    // Whitespace
    32 -> Key.Space,
    9  -> Key.Tab,
    13 -> Key.Enter,
    8  -> Key.Backspace,
    // Accents
    172 -> Key.Backtick,
    192 -> Key.Backtick,
    // MISSING: 94  -> Key.Caret,
    196 -> Key.Tilde,
    // Control
    27  -> Key.Escape,
    16  -> Key.Shift,
    17  -> Key.Ctrl,
    18  -> Key.Alt,
    224 -> Key.Meta,
    // Arrows
    38 -> Key.Up,
    40 -> Key.Down,
    37 -> Key.Left,
    39 -> Key.Right,
    // Punctuation
    190 -> Key.Period,
    58  -> Key.Colon,
    189 -> Key.Comma,
    59  -> Key.Semicolon,
    161 -> Key.ExclamationMark,
    // MISSING: 63  -> Key.QuestionMark,
    // Quotes
    222 -> Key.SingleQuote,
    162 -> Key.DoubleQuote,
    // Slashes
    191 -> Key.Slash,
    220 -> Key.Backslash,
    167 -> Key.Underscore,
    // Math
    171 -> Key.Plus,
    173 -> Key.Minus,
    170 -> Key.Asterisk,
    61  -> Key.Equals,
    // MISSING: 37  -> Key.Percentage,
    // Brackets
    168 -> Key.OpenParenthesis,
    169 -> Key.CloseParenthesis,
    40  -> Key.OpenParenthesis,
    41  -> Key.CloseParenthesis,
    219 -> Key.OpenBracket,
    221 -> Key.CloseBracket,
    // Other
    166 -> Key.Ampersand,
    164 -> Key.DollarSign,
    64  -> Key.At,
    163 -> Key.Hash
  )
}
