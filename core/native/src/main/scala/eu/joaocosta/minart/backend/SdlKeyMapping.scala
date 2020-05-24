package eu.joaocosta.minart.backend

import sdl2.Extras._
import sdl2.SDL._

import eu.joaocosta.minart.core.KeyboardInput._

object SdlKeyMapping extends KeyMapping[SDL_Keycode] {
  protected final val mappings: Map[Int, Key] = Map(
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

    SDLK_SPACE -> Key.Space,
    SDLK_KP_SPACE -> Key.Space,
    SDLK_TAB -> Key.Tab,
    SDLK_KP_TAB -> Key.Tab,
    SDLK_RETURN -> Key.Enter,
    SDLK_RETURN2 -> Key.Enter,
    SDLK_KP_ENTER -> Key.Enter,
    SDLK_BACKSPACE -> Key.Backspace,
    SDLK_KP_BACKSPACE -> Key.Backspace,

    SDLK_LSHIFT -> Key.Shift,
    SDLK_RSHIFT -> Key.Shift,
    SDLK_LCTRL -> Key.Ctrl,
    SDLK_RCTRL -> Key.Ctrl,
    SDLK_LALT -> Key.Alt,
    SDLK_RALT -> Key.Alt,
    SDLK_LGUI -> Key.Meta,
    SDLK_RGUI -> Key.Meta,

    SDLK_UP -> Key.Up,
    SDLK_DOWN -> Key.Down,
    SDLK_LEFT -> Key.Left,
    SDLK_RIGHT -> Key.Right)
}
