package eu.joaocosta.minart.backend

import sdl2.all.*
import sdl2.enumerations.SDL_KeyCode.*

import eu.joaocosta.minart.input.KeyboardInput.*

/** Key mappings for the Native platform, backed by SDL.
  */
object SdlKeyMapping extends KeyMapping[SDL_KeyCode] {
  protected val mappings: Map[SDL_KeyCode, Key] = Map(
    // Letters
    SDLK_a -> Key.A,
    SDLK_b -> Key.B,
    SDLK_c -> Key.C,
    SDLK_d -> Key.D,
    SDLK_e -> Key.E,
    SDLK_f -> Key.F,
    SDLK_g -> Key.G,
    SDLK_h -> Key.H,
    SDLK_i -> Key.I,
    SDLK_j -> Key.J,
    SDLK_k -> Key.K,
    SDLK_l -> Key.L,
    SDLK_m -> Key.M,
    SDLK_n -> Key.N,
    SDLK_o -> Key.O,
    SDLK_p -> Key.P,
    SDLK_q -> Key.Q,
    SDLK_r -> Key.R,
    SDLK_s -> Key.S,
    SDLK_t -> Key.T,
    SDLK_u -> Key.U,
    SDLK_v -> Key.V,
    SDLK_w -> Key.W,
    SDLK_x -> Key.X,
    SDLK_y -> Key.Y,
    SDLK_z -> Key.Z,
    // Numbers
    SDLK_0 -> Key.Digit0,
    SDLK_1 -> Key.Digit1,
    SDLK_2 -> Key.Digit2,
    SDLK_3 -> Key.Digit3,
    SDLK_4 -> Key.Digit4,
    SDLK_5 -> Key.Digit5,
    SDLK_6 -> Key.Digit6,
    SDLK_7 -> Key.Digit7,
    SDLK_8 -> Key.Digit8,
    SDLK_9 -> Key.Digit9,
    // Numpad Numbers
    SDLK_KP_0 -> Key.NumPad0,
    SDLK_KP_1 -> Key.NumPad1,
    SDLK_KP_2 -> Key.NumPad2,
    SDLK_KP_3 -> Key.NumPad3,
    SDLK_KP_4 -> Key.NumPad4,
    SDLK_KP_5 -> Key.NumPad5,
    SDLK_KP_6 -> Key.NumPad6,
    SDLK_KP_7 -> Key.NumPad7,
    SDLK_KP_8 -> Key.NumPad8,
    SDLK_KP_9 -> Key.NumPad9,
    // Whitespace
    SDLK_SPACE        -> Key.Space,
    SDLK_KP_SPACE     -> Key.Space,
    SDLK_TAB          -> Key.Tab,
    SDLK_KP_TAB       -> Key.Tab,
    SDLK_RETURN       -> Key.Enter,
    SDLK_RETURN2      -> Key.Enter,
    SDLK_KP_ENTER     -> Key.Enter,
    SDLK_BACKSPACE    -> Key.Backspace,
    SDLK_KP_BACKSPACE -> Key.Backspace,
    // Control
    SDLK_ESCAPE -> Key.Escape,
    SDLK_LSHIFT -> Key.Shift,
    SDLK_RSHIFT -> Key.Shift,
    SDLK_LCTRL  -> Key.Ctrl,
    SDLK_RCTRL  -> Key.Ctrl,
    SDLK_LALT   -> Key.Alt,
    SDLK_RALT   -> Key.Alt,
    SDLK_LGUI   -> Key.Meta,
    SDLK_RGUI   -> Key.Meta,
    // Arrows
    SDLK_UP    -> Key.Up,
    SDLK_DOWN  -> Key.Down,
    SDLK_LEFT  -> Key.Left,
    SDLK_RIGHT -> Key.Right,
    // Punctuation
    SDLK_PERIOD    -> Key.Period,
    SDLK_KP_PERIOD -> Key.Period,
    SDLK_COLON     -> Key.Colon,
    SDLK_KP_COLON  -> Key.Colon,
    SDLK_COMMA     -> Key.Comma,
    SDLK_SEMICOLON -> Key.Semicolon,
    SDLK_EXCLAIM   -> Key.ExclamationMark,
    SDLK_KP_EXCLAM -> Key.ExclamationMark,
    SDLK_QUESTION  -> Key.QuestionMark,
    // Quotes
    SDLK_QUOTE    -> Key.SingleQuote,
    SDLK_QUOTEDBL -> Key.DoubleQuote,
    // Slashes
    SDLK_SLASH          -> Key.Slash,
    SDLK_KP_DIVIDE      -> Key.Slash,
    SDLK_BACKSLASH      -> Key.Backslash,
    SDLK_KP_VERTICALBAR -> Key.VerticalBar,
    SDLK_UNDERSCORE     -> Key.Underscore,
    // Math
    SDLK_PLUS      -> Key.Plus,
    SDLK_KP_PLUS   -> Key.Plus,
    SDLK_MINUS     -> Key.Minus,
    SDLK_KP_MINUS  -> Key.Minus,
    SDLK_ASTERISK  -> Key.Asterisk,
    SDLK_EQUALS    -> Key.Equals,
    SDLK_KP_EQUALS -> Key.Equals,
    // Brackets
    SDLK_LEFTPAREN     -> Key.OpenParenthesis,
    SDLK_KP_LEFTPAREN  -> Key.OpenParenthesis,
    SDLK_RIGHTPAREN    -> Key.CloseParenthesis,
    SDLK_KP_RIGHTPAREN -> Key.CloseParenthesis,
    // Other
    SDLK_AMPERSAND    -> Key.Ampersand,
    SDLK_KP_AMPERSAND -> Key.Ampersand,
    SDLK_DOLLAR       -> Key.DollarSign,
    SDLK_KP_AT        -> Key.At,
    SDLK_AT           -> Key.At
  )
}
