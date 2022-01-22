package eu.joaocosta.minart.runtime.pure

import scala.concurrent._
import scala.util.{Failure, Success}

import verify._

object PollSpec extends BasicTestSuite {
  test("store pure results") {
    val error = new Exception("error")
    assert(Poll.successful(1).poll.run(()) == Some(Success(1)))
    assert(Poll.failed(error).poll.run(()) == Some(Failure(error)))
    assert(Poll.never.poll.run(()) == None)
  }

  test("allow polling Futures") {
    val promise = Promise[Int]()
    val io      = Poll.fromFuture(promise.future)
    assert(io.poll.run(()) == None)
    promise.complete(scala.util.Success(0))
    assert(io.poll.run(()) == Some(Success(0)))
  }

  test("provide a stack-safe transform operation") {
    val io = (1 to 1000).foldLeft[Poll[Int]](Poll.failed(new Exception("error"))) { case (acc, _) =>
      acc.transform(_.recover { case _ => 0 }.map(_ + 1))
    }
    assert(io.poll.run(()) == Some(Success(1000)))
  }

  test("provide a stack-safe map operation") {
    val io = (1 to 1000).foldLeft[Poll[Int]](Poll.successful(0)) { case (acc, _) => acc.map(_ + 1) }
    assert(io.poll.run(()) == Some(Success(1000)))
  }

  test("provide a stack-safe flatMap operation") {
    val io = (1 to 1000).foldLeft[Poll[Int]](Poll.successful(0)) { case (acc, _) =>
      acc.flatMap(x => Poll.successful(x + 1))
    }
    assert(io.poll.run(()) == Some(Success(1000)))
  }

  test("provide zip/zipWith operations") {
    val zipPoll = Poll.successful(1).zip(Poll.successful(2))
    assert(zipPoll.poll.run(()) == Some(Success((1, 2))))

    val zipWithPoll = Poll.successful(1).zipWith(Poll.successful(2))(_ + _)
    assert(zipWithPoll.poll.run(()) == Some(Success(3)))
  }

  test("correctly sequence operations") {
    val io = Poll.sequence(List(Poll.successful(1), Poll.successful(2), Poll.successful(3)))
    assert(io.poll.run(()) == Some(Success(List(1, 2, 3))))
  }
}
