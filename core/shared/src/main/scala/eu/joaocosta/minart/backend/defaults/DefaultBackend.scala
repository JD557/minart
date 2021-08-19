package eu.joaocosta.minart.backend.defaults

/** Typeclass to fetch an implicit default backend (e.g. Canvas or RenderLoop) for a cetain platform. This is used to
  * power the `Backend.default()` implementations. Ideally, an end user of the library should not need to implement
  * this.
  */
trait DefaultBackend[-A, +B] {
  def defaultValue(params: A): B
  def defaultValue()(implicit ev: Any <:< A): B = defaultValue(ev(()))
}

object DefaultBackend {
  def fromFunction[A, B](f: A => B): DefaultBackend[A, B] = new DefaultBackend[A, B] {
    def defaultValue(params: A): B = f(params)
  }

  def fromConstant[B](value: B): DefaultBackend[Any, B] = new DefaultBackend[Any, B] {
    def defaultValue(params: Any): B = value
  }
}
