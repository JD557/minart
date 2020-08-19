package eu.joaocosta.minart.pure

import scala.concurrent._

import org.specs2.mutable._

import eu.joaocosta.minart.core._

class RIOSpec extends Specification {
  "A RIO" should {
    "store pure results" in {
      RIO.pure(1).run(()) === 1
    }

    "suspend computations" in {
      var hasRun: Boolean = false
      val io = RIO.suspend({ hasRun = true })
      hasRun === false
      io.run(())
      hasRun === true
    }

    "allow polling Futures" in {
      val promise = Promise[Int]
      val io = RIO.pollFuture(promise.future)
      io.run(()) === None
      promise.complete(scala.util.Success(0))
      io.run(()) === Some(scala.util.Success(0))
    }

    "provide a stack-safe map operation" in {
      val io = (1 to 1000).foldLeft[RIO[Any, Int]](RIO.pure(0)) { case (acc, _) => acc.map(_ + 1) }
      io.run(()) === 1000
    }

    "provide a stack-safe flatMap operation" in {
      val io = (1 to 1000).foldLeft[RIO[Any, Int]](RIO.pure(0)) { case (acc, _) => acc.flatMap(x => RIO.pure(x + 1)) }
      io.run(()) === 1000
    }

    "provide zip/zipWith operations" in {
      RIO.pure(1).zip(RIO.pure(2)).run(()) === (1, 2)

      RIO.pure(1).zipWith(RIO.pure(2))(_ + _).run(()) === 3
    }

    "provide andThen/andFinally operations" in {
      var hasRunAndThen = false
      RIO.suspend({ hasRunAndThen = true; 1 }).andThen(RIO.pure(2)).run(()) === 2
      hasRunAndThen === true

      var hasRunAndFinally = false
      RIO.pure(1).andFinally(RIO.suspend({ hasRunAndFinally = true; 2 })).run(()) === 1
      hasRunAndFinally === true
    }

    "correctly sequence operations" in {
      val io = RIO.sequence(List(RIO.pure(1), RIO.pure(2), RIO.pure(3)))
      io.run(()) === List(1, 2, 3)
    }
  }
}
