package eu.joaocosta.minart.runtime.pure

/** Representation of an effectful operation, based on Haskell's RIO Monad.
  */
sealed trait RIO[-R, +A] {

  /** Runs this operation. */
  def run(resource: R): A

  /** Maps the result of this operation. */
  def map[B](f: A => B): RIO[R, B]

  /** Combines two operations by applying a function to the result of the first operation. */
  def flatMap[RR <: R, B](f: A => RIO[RR, B]): RIO[RR, B] = RIO.FlatMap[RR, A, B](this, f)

  /** Combines two operations by discarding the result of the first operation. */
  def andThen[RR <: R, B](that: RIO[RR, B]): RIO[RR, B] = RIO.FlatMap[RR, A, B](this, _ => that)

  /** Combines two operations by discarding the result of the second operation. */
  def andFinally[RR <: R, B](that: RIO[RR, B]): RIO[RR, A] = RIO.FlatMap[RR, A, A](this, x => that.as(x))

  /** Combines two operations by combining their results with the given function. */
  def zipWith[RR <: R, B, C](that: RIO[RR, B])(f: (A, B) => C): RIO[RR, C] = this.flatMap(x => that.map(y => f(x, y)))

  /** Combines two operations by combining their results into a tuple. */
  def zip[RR <: R, B](that: RIO[RR, B]): RIO[RR, (A, B)] = this.zipWith(that)((x, y) => x -> y)

  /** Changes the result of this operation to another value. */
  def as[B](x: B): RIO[R, B] = this.map(_ => x)

  /** Transforms the resource required by this operation. */
  def contramap[RR](f: RR => R): RIO[RR, A] = RIO.Suspend[RR, A](res => this.run(f(res)))

  /** Provides the required resource to this operation. */
  def provide(res: R): RIO[Any, A] = this.contramap(_ => res)

  /** Changes the result of this operation unit. */
  lazy val unit: RIO[R, Unit] = this.as(())
}

object RIO extends IOOps.IOBaseOps[Any] {
  private[pure] final case class Suspend[R, A](thunk: R => A) extends RIO[R, A] {
    def run(resource: R): A          = thunk(resource)
    def map[B](f: A => B): RIO[R, B] = Suspend(thunk.andThen(f))
  }
  private[pure] final case class FlatMap[R, A, B](io: RIO[R, A], andThen: A => RIO[R, B]) extends RIO[R, B] {
    def run(resource: R): B          = andThen(io.run(resource)).run(resource)
    def map[C](f: B => C): RIO[R, C] = FlatMap[R, B, C](this, x => Suspend(_ => f(x)))
  }

  /** Returns a operation that requires some resource. */
  def access[R, A](f: R => A): RIO[R, A] = RIO.Suspend[R, A](f)

  /** Runs a computation only if the predicate is true, otherwise does nothing */
  def when[R](predicate: Boolean)(io: => RIO[R, Unit]): RIO[R, Unit] =
    if (predicate) io else RIO.noop

  /** Converts an `Iterable[RIO[R, A]]` into a `RIO[R, List[A]]`. */
  def sequence[R, A](it: Iterable[RIO[R, A]]): RIO[R, List[A]] =
    access(res => it.map(_.run(res)).toList)

  /** Converts an `Iterable[RIO[R, A]]` into a `RIO[R, Unit]`. */
  def sequence_[R](it: Iterable[RIO[R, Any]]): RIO[R, Unit] =
    access(res => it.foreach(_.run(res)))

  /** Converts an `Iterable[A]` into a `RIO[R, List[B]]` by applying an operation to each element. */
  def traverse[R, A, B](it: Iterable[A])(f: A => RIO[R, B]): RIO[R, List[B]] =
    access(res => it.map(x => f(x).run(res)).toList)

  /** Applies an operation to each element of a `Iterable[A]` and discards the result. */
  def foreach[R, A](it: Iterable[A])(f: A => RIO[R, Any]): RIO[R, Unit] =
    access(res => it.foreach(x => f(x).run(res)))

  /** Applies an operation to each element of a `Iterator[A]` and discards the result. */
  def foreach[R, A](it: () => Iterator[A])(f: A => RIO[R, Any]): RIO[R, Unit] =
    access(res => it().foreach(x => f(x).run(res)))
}
