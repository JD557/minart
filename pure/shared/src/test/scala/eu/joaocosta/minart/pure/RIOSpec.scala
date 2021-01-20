package eu.joaocosta.minart.pure

import scala.concurrent._

import verify._

import eu.joaocosta.minart.core._

object RIOSpec extends BasicTestSuite {
  test("store pure results") {
    assert(RIO.pure(1).run(()) == 1)
  }

  test("suspend computations") {
    var hasRun: Boolean = false
    val io = RIO.suspend({ hasRun = true })
    assert(hasRun == false)
    io.run(())
    assert(hasRun == true)
  }

  test("allow polling Futures") {
    val promise = Promise[Int]()
    val io = RIO.pollFuture(promise.future)
    assert(io.run(()) == None)
    promise.complete(scala.util.Success(0))
    assert(io.run(()) == Some(scala.util.Success(0)))
  }

  test("provide a stack-safe map operation") {
    val io = (1 to 1000).foldLeft[RIO[Any, Int]](RIO.pure(0)) { case (acc, _) => acc.map(_ + 1) }
    assert(io.run(()) == 1000)
  }

  test("provide a stack-safe flatMap operation") {
    val io = (1 to 1000).foldLeft[RIO[Any, Int]](RIO.pure(0)) { case (acc, _) => acc.flatMap(x => RIO.pure(x + 1)) }
    assert(io.run(()) == 1000)
  }

  test("provide zip/zipWith operations") {
    assert(RIO.pure(1).zip(RIO.pure(2)).run(()) == (1, 2))

    assert(RIO.pure(1).zipWith(RIO.pure(2))(_ + _).run(()) == 3)
  }

  test("provide andThen/andFinally operations") {
    var hasRunAndThen = false
    assert(RIO.suspend({ hasRunAndThen = true; 1 }).andThen(RIO.pure(2)).run(()) == 2)
    assert(hasRunAndThen == true)

    var hasRunAndFinally = false
    assert(RIO.pure(1).andFinally(RIO.suspend({ hasRunAndFinally = true; 2 })).run(()) == 1)
    assert(hasRunAndFinally == true)
  }

  test("correctly sequence operations") {
    val io = RIO.sequence(List(RIO.pure(1), RIO.pure(2), RIO.pure(3)))
    assert(io.run(()) == List(1, 2, 3))
  }
}
