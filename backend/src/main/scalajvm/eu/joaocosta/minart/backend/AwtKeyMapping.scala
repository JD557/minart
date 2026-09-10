package eu.joaocosta.minart.backend

import java.awt.event.KeyEvent

import eu.joaocosta.minart.input.KeyboardInput.*

/** Key mappings for the Java platform, backed by AWT.
  */
object AwtKeyMapping extends KeyMapping[Int] {
  protected val mappings: Map[Int, Key] = Map(
    // Letters
    KeyEvent.VK_A -> Key.A,
    KeyEvent.VK_B -> Key.B,
    KeyEvent.VK_C -> Key.C,
    KeyEvent.VK_D -> Key.D,
    KeyEvent.VK_E -> Key.E,
    KeyEvent.VK_F -> Key.F,
    KeyEvent.VK_G -> Key.G,
    KeyEvent.VK_H -> Key.H,
    KeyEvent.VK_I -> Key.I,
    KeyEvent.VK_J -> Key.J,
    KeyEvent.VK_K -> Key.K,
    KeyEvent.VK_L -> Key.L,
    KeyEvent.VK_M -> Key.M,
    KeyEvent.VK_N -> Key.N,
    KeyEvent.VK_O -> Key.O,
    KeyEvent.VK_P -> Key.P,
    KeyEvent.VK_Q -> Key.Q,
    KeyEvent.VK_R -> Key.R,
    KeyEvent.VK_S -> Key.S,
    KeyEvent.VK_T -> Key.T,
    KeyEvent.VK_U -> Key.U,
    KeyEvent.VK_V -> Key.V,
    KeyEvent.VK_W -> Key.W,
    KeyEvent.VK_X -> Key.X,
    KeyEvent.VK_Y -> Key.Y,
    KeyEvent.VK_Z -> Key.Z,
    // Numbers
    KeyEvent.VK_0 -> Key.Digit0,
    KeyEvent.VK_1 -> Key.Digit1,
    KeyEvent.VK_2 -> Key.Digit2,
    KeyEvent.VK_3 -> Key.Digit3,
    KeyEvent.VK_4 -> Key.Digit4,
    KeyEvent.VK_5 -> Key.Digit5,
    KeyEvent.VK_6 -> Key.Digit6,
    KeyEvent.VK_7 -> Key.Digit7,
    KeyEvent.VK_8 -> Key.Digit8,
    KeyEvent.VK_9 -> Key.Digit9,
    // Numpad Numbers
    KeyEvent.VK_NUMPAD0 -> Key.NumPad0,
    KeyEvent.VK_NUMPAD1 -> Key.NumPad1,
    KeyEvent.VK_NUMPAD2 -> Key.NumPad2,
    KeyEvent.VK_NUMPAD3 -> Key.NumPad3,
    KeyEvent.VK_NUMPAD4 -> Key.NumPad4,
    KeyEvent.VK_NUMPAD5 -> Key.NumPad5,
    KeyEvent.VK_NUMPAD6 -> Key.NumPad6,
    KeyEvent.VK_NUMPAD7 -> Key.NumPad7,
    KeyEvent.VK_NUMPAD8 -> Key.NumPad8,
    KeyEvent.VK_NUMPAD9 -> Key.NumPad9,
    // Whitespace
    KeyEvent.VK_SPACE      -> Key.Space,
    KeyEvent.VK_TAB        -> Key.Tab,
    KeyEvent.VK_ENTER      -> Key.Enter,
    KeyEvent.VK_BACK_SPACE -> Key.Backspace,
    // Accents
    KeyEvent.VK_BACK_QUOTE      -> Key.Backtick,
    KeyEvent.VK_CIRCUMFLEX      -> Key.Caret,
    KeyEvent.VK_DEAD_CIRCUMFLEX -> Key.Caret,
    KeyEvent.VK_DEAD_TILDE      -> Key.Tilde,
    // Control
    KeyEvent.VK_ESCAPE  -> Key.Escape,
    KeyEvent.VK_SHIFT   -> Key.Shift,
    KeyEvent.VK_CONTROL -> Key.Ctrl,
    KeyEvent.VK_ALT     -> Key.Alt,
    KeyEvent.VK_META    -> Key.Meta,
    KeyEvent.VK_WINDOWS -> Key.Meta,
    // Arrows
    KeyEvent.VK_UP    -> Key.Up,
    KeyEvent.VK_DOWN  -> Key.Down,
    KeyEvent.VK_LEFT  -> Key.Left,
    KeyEvent.VK_RIGHT -> Key.Right,
    // Punctuation
    KeyEvent.VK_PERIOD           -> Key.Period,
    KeyEvent.VK_DECIMAL          -> Key.Period,
    KeyEvent.VK_COLON            -> Key.Colon,
    KeyEvent.VK_COMMA            -> Key.Comma,
    KeyEvent.VK_SEMICOLON        -> Key.Semicolon,
    KeyEvent.VK_EXCLAMATION_MARK -> Key.ExclamationMark,
    // MISSING: KeyEvent.VK_QUESTION_MARK    -> Key.QuestionMark,
    // Quotes
    KeyEvent.VK_QUOTE    -> Key.SingleQuote,
    KeyEvent.VK_QUOTEDBL -> Key.DoubleQuote,
    // Slashes
    KeyEvent.VK_SLASH      -> Key.Slash,
    KeyEvent.VK_DIVIDE     -> Key.Slash,
    KeyEvent.VK_BACK_SLASH -> Key.Backslash,
    KeyEvent.VK_UNDERSCORE -> Key.Underscore,
    // MISSING: KeyEvent.VK_PIPE       -> Key.Pipe,
    // Math
    KeyEvent.VK_PLUS     -> Key.Plus,
    KeyEvent.VK_ADD      -> Key.Plus,
    KeyEvent.VK_MINUS    -> Key.Minus,
    KeyEvent.VK_SUBTRACT -> Key.Minus,
    KeyEvent.VK_ASTERISK -> Key.Asterisk,
    KeyEvent.VK_MULTIPLY -> Key.Asterisk,
    KeyEvent.VK_EQUALS   -> Key.Equals,
    // MISSING: KeyEvent.VK_PERCENT  -> Key.Parcentage,
    // Brackets
    KeyEvent.VK_LEFT_PARENTHESIS  -> Key.OpenParenthesis,
    KeyEvent.VK_RIGHT_PARENTHESIS -> Key.CloseParenthesis,
    KeyEvent.VK_OPEN_BRACKET      -> Key.OpenBracket,
    KeyEvent.VK_CLOSE_BRACKET     -> Key.CloseBracket,
    // Other
    KeyEvent.VK_AMPERSAND   -> Key.Ampersand,
    KeyEvent.VK_DOLLAR      -> Key.DollarSign,
    KeyEvent.VK_AT          -> Key.At,
    KeyEvent.VK_NUMBER_SIGN -> Key.Hash
  )
}
