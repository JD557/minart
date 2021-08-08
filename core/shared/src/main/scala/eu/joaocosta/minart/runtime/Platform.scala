package eu.joaocosta.minart.runtime

import eu.joaocosta.minart.backend.defaults.DefaultBackend

/** Runtime representation of the runtime platform.
  * In general, it is preferable to handle platform-specific code at compile
  * time, but this can be helpful for small changes.
  */
sealed trait Platform

object Platform {

  /** Returns the current runtime plaform.
    * In general, it is preferable to handle platform-specific code at compile
    * time, but this can be helpful for small changes.
    */
  def apply()(implicit d: DefaultBackend[Any, Platform]): Platform =
    d.defaultValue(())

  case object JVM    extends Platform
  case object JS     extends Platform
  case object Native extends Platform
}
