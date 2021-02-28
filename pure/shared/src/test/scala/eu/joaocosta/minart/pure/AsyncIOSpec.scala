package eu.joaocosta.minart.pure

import scala.concurrent._
import scala.util.{Success, Failure}

import verify._

import eu.joaocosta.minart.core._

object AsyncSpec extends BasicTestSuite {
  test("store pure results") {
    val error = new Exception("error")
    assert(AsyncIO.successful(1).poll.run(()) == Some(Success(1)))
    assert(AsyncIO.failed(error).poll.run(()) == Some(Failure(error)))
    assert(AsyncIO.never.poll.run(()) == None)
  }

  test("allow polling Futures") {
    val promise = Promise[Int]()
    val io      = AsyncIO.fromFuture(promise.future)
    assert(io.poll.run(()) == None)
    promise.complete(scala.util.Success(0))
    assert(io.poll.run(()) == Some(Success(0)))
  }

  test("allow polling async operations") {
    var completer: Int => Unit = _ => ()
    val io = AsyncIO
      .fromCallback[Int] { cb =>
        completer = (x: Int) => cb(Success(x))
      }
      .run(())
    assert(io.poll.run(()) == None)
    completer(0)
    assert(io.poll.run(()) == Some(Success(0)))
  }

  test("provide a stack-safe transform operation") {
    val io = (1 to 1000).foldLeft[AsyncIO[Int]](AsyncIO.failed(new Exception("error"))) { case (acc, _) =>
      acc.transform(_.recover(_ => 0).map(_ + 1))
    }
    assert(io.poll.run(()) == Some(Success(1000)))
  }

  test("provide a stack-safe map operation") {
    val io = (1 to 1000).foldLeft[AsyncIO[Int]](AsyncIO.successful(0)) { case (acc, _) => acc.map(_ + 1) }
    assert(io.poll.run(()) == Some(Success(1000)))
  }

  test("provide a stack-safe flatMap operation") {
    val io = (1 to 1000).foldLeft[AsyncIO[Int]](AsyncIO.successful(0)) { case (acc, _) =>
      acc.flatMap(x => AsyncIO.successful(x + 1))
    }
    assert(io.poll.run(()) == Some(Success(1000)))
  }

  test("provide zip/zipWith operations") {
    assert(AsyncIO.successful(1).zip(AsyncIO.successful(2)).poll.run(()) == Some(Success((1, 2))))

    assert(AsyncIO.successful(1).zipWith(AsyncIO.successful(2))(_ + _).poll.run(()) == Some(Success(3)))
  }

  test("correctly sequence operations") {
    val io = AsyncIO.sequence(List(AsyncIO.successful(1), AsyncIO.successful(2), AsyncIO.successful(3)))
    assert(io.poll.run(()) == Some(Success(List(1, 2, 3))))
  }
}
