package eu.joaocosta.minart.runtime

import eu.joaocosta.minart.backend.defaults.DefaultBackend

/** Runtime representation of the runtime platform.
  * In general, it is preferable to handle platform-specific code at compile
  * time, but this can be helpful for small changes.
  */
enum Platform {
  case JVM
  case JS
  case Native
}

object Platform {

  /** Returns the current runtime plaform.
    * In general, it is preferable to handle platform-specific code at compile
    * time, but this can be helpful for small changes.
    */
  def apply()(using d: DefaultBackend[Any, Platform]): Platform =
    d.defaultValue(())
}
