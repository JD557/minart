package eu.joaocosta.minart.runtime.pure

import verify._

object RIOSpec extends BasicTestSuite {
  test("store pure results") {
    assert(RIO.pure(1).run(()) == 1)
  }

  test("suspend computations") {
    var hasRun: Boolean = false
    val io              = RIO.suspend({ hasRun = true })
    assert(hasRun == false)
    io.run(())
    assert(hasRun == true)
  }

  test("allow polling async operations") {
    var completer: Int => Unit = (_) => ()
    val io = RIO
      .fromCallback[Int] { cb =>
        completer = (x: Int) => cb(scala.util.Success(x))
      }
      .run(())
    assert(io.poll.run(()) == None)
    completer(0)
    assert(io.poll.run(()) == Some(scala.util.Success(0)))
  }

  test("provide a stack-safe map operation") {
    val io = (1 to 1000).foldLeft[RIO[Any, Int]](RIO.pure(0)) { case (acc, _) => acc.map(_ + 1) }
    assert(io.run(()) == 1000)
  }

  test("provide a stack-safe flatMap operation") {
    // pure(0).flatMap(pure(_ + 1)).flatMap(pure(_ + 1)).flatMap...
    val ioA = (1 to 1000).foldLeft[RIO[Any, Int]](RIO.pure(0)) { case (acc, _) => acc.flatMap(x => RIO.pure(x + 1)) }
    assert(ioA.run(()) == 1000)
    // pure(0).flatMap(x => pure( + 1).flatMap(x => (pure(x + 1).flatMap...))
    val ioB = {
      def loop(x: Int): RIO[Any, Int] =
        if (x >= 1000) RIO.pure(x)
        else RIO.pure(x + 1).flatMap(loop)
      loop(0)
    }
    assert(ioB.run(()) == 1000)
  }

  test("provide access/contramap/provide operations") {
    val originalIo     = RIO.access[Int, Int](x => x)
    val contramappedIo = originalIo.contramap[Int](x => x + 1)
    assert(originalIo.run(0) == 0)
    assert(originalIo.provide(0).run(()) == 0)
    assert(contramappedIo.run(0) == 1)
    assert(contramappedIo.provide(0).run(()) == 1)
  }

  test("provide zip/zipWith operations") {
    val zipIo = RIO.pure(1).zip(RIO.pure(2))
    assert(zipIo.run(()) == (1, 2))

    val zipWithIo = RIO.pure(1).zipWith(RIO.pure(2))(_ + _)
    assert(zipWithIo.run(()) == 3)
  }

  test("provide andThen/andFinally operations") {
    var hasRunAndThen = false
    assert(RIO.suspend({ hasRunAndThen = true; 1 }).andThen(RIO.pure(2)).run(()) == 2)
    assert(hasRunAndThen == true)

    var hasRunAndFinally = false
    assert(RIO.pure(1).andFinally(RIO.suspend({ hasRunAndFinally = true; 2 })).run(()) == 1)
    assert(hasRunAndFinally == true)
  }

  test("correctly sequence/traverse results") {
    val seqIo      = RIO.sequence(List(RIO.pure(1), RIO.pure(2), RIO.pure(3)))
    val traverseIo = RIO.traverse(List(0, 1, 2))(x => RIO.pure(x + 1))
    assert(seqIo.run(()) == List(1, 2, 3))
    assert(traverseIo.run(()) == List(1, 2, 3))
  }

  test("correctly sequence/traverse side-effects") {
    val buffer = new collection.mutable.ListBuffer[String]()
    def addValue(x: String): RIO[Any, String] = RIO.suspend {
      buffer += x
      x
    }

    val io = for {
      _ <- RIO.sequence(List(addValue("sequence1"), addValue("sequence2"), addValue("sequence3")))
      _ <- RIO.sequence_(List(addValue("sequence_1"), addValue("sequence_2"), addValue("sequence_3")))
      _ <- RIO.traverse(List(1, 2, 3))(x => addValue(s"traverse$x"))
      _ <- RIO.foreach(List(1, 2, 3))(x => addValue(s"foreach$x"))
    } yield buffer.result()

    assert(
      io.run(()) ==
        List(
          "sequence1",
          "sequence2",
          "sequence3",
          "sequence_1",
          "sequence_2",
          "sequence_3",
          "traverse1",
          "traverse2",
          "traverse3",
          "foreach1",
          "foreach2",
          "foreach3"
        )
    )
  }
}
