package eu.joaocosta.minart.backend.defaults

import scala.annotation.implicitNotFound

/** Typeclass to fetch an implicit default backend (e.g. Canvas or RenderLoop) for a cetain platform.
  * This is used to power the `Backend.apply()`/`Backend.create(settings)` implementations.
  * Ideally, an end user of the library should not need to implement this.
  */
@implicitNotFound(
  "Default backend not found.\nIf you want to use the default backends import eu.joaocosta.minart.backend.defaults.given and add the minart-backend dependency."
)
trait DefaultBackend[-A, +B] {
  def defaultValue(params: A): B
  final def defaultValue()(using ev: Any <:< A): B = defaultValue(ev(()))
}

object DefaultBackend {
  def apply[A, B](using backend: DefaultBackend[A, B]) = backend

  def fromFunction[A, B](f: A => B): DefaultBackend[A, B] = new DefaultBackend[A, B] {
    def defaultValue(params: A): B = f(params)
  }

  def fromConstant[B](value: B): DefaultBackend[Any, B] = new DefaultBackend[Any, B] {
    def defaultValue(params: Any): B = value
  }
}
