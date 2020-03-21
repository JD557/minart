package eu.joaocosta.minart.pure

import eu.joaocosta.minart._

sealed trait CanvasIO[+A] {
  def run(canvas: Canvas): A
  def map[B](f: A => B): CanvasIO[B]
  def flatMap[B](f: A => CanvasIO[B]): CanvasIO[B] = CanvasIO.FlatMap[A, B](this, f)
  def andThen[B](x: CanvasIO[B]): CanvasIO[B] = CanvasIO.FlatMap[A, B](this, _ => x)
  lazy val unit = this.map(_ => ())
}

object CanvasIO {
  private case class Suspend[A](thunk: Canvas => A) extends CanvasIO[A] {
    def run(canvas: Canvas): A = thunk(canvas)
    def map[B](f: A => B): CanvasIO[B] = Suspend(thunk.andThen(f))
  }
  private case class FlatMap[A, B](io: CanvasIO[A], andThen: A => CanvasIO[B]) extends CanvasIO[B] {
    def run(canvas: Canvas): B = andThen(io.run(canvas)).run(canvas)
    def map[C](f: B => C): CanvasIO[C] = FlatMap[B, C](this, x => Suspend(_ => f(x)))
  }

  def pure[A](x: A): CanvasIO[A] = Suspend[A](_ => x)
  val getWidth: CanvasIO[Int] = Suspend[Int](_.width)
  val getHeight: CanvasIO[Int] = Suspend[Int](_.height)
  def putPixel(x: Int, y: Int, color: Color): CanvasIO[Unit] = Suspend[Unit](_.putPixel(x, y, color))
  def getBackbufferPixel(x: Int, y: Int): CanvasIO[Color] = Suspend[Color](_.getBackbufferPixel(x, y))
  val clear: CanvasIO[Unit] = Suspend[Unit](_.clear())
  val redraw: CanvasIO[Unit] = Suspend[Unit](_.redraw())

  def sequence[A, B](in: Iterable[A])(f: A => CanvasIO[B]): CanvasIO[List[B]] =
    Suspend(canvas => in.toList.map(f).map(_.run(canvas)))
  def forEach[A](in: Iterable[A])(f: A => CanvasIO[Any]): CanvasIO[Unit] =
    in.toList.map(f).foldLeft[CanvasIO[Unit]](Suspend(_ => ())) { case (acc, next) => acc.andThen(next.unit) }
}
