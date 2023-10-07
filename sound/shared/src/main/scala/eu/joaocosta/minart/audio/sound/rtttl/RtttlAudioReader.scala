package eu.joaocosta.minart.audio.sound.rtttl

import java.io.InputStream

import eu.joaocosta.minart.audio._
import eu.joaocosta.minart.audio.sound._
import eu.joaocosta.minart.internal._

/** Audio reader for RTTTL files.
  */
trait RtttlAudioReader extends AudioClipReader {

  val oscilator: Oscillator
  import RtttlAudioReader._
  import ByteReader._
  import ByteStringOps._

  private def parseHeader(jintu: String, defaultValue: String): Either[String, Header] = {
    val defaultSection = defaultValue.split(",").map(_.split("="))
    if (defaultSection.size != 3 || defaultSection.forall(_.size != 2)) {
      Left(s"Invalid default section: $defaultValue")
    } else {
      val defaultSectionMap = defaultSection.map(entry => entry.head -> entry.last.toIntOption).toMap
      if (defaultSectionMap.valuesIterator.contains(None)) Left("Failed to parse number in header")
      else {
        val duration = defaultSectionMap.get("d").flatten
        val octave   = defaultSectionMap.get("o").flatten
        val beat     = defaultSectionMap.get("b").flatten
        if (duration.isEmpty || octave.isEmpty || beat.isEmpty)
          Left(s"Expected d, o and b keys in the header, got ${defaultSectionMap.keySet}")
        else
          Right(Header(jintu, duration.get, octave.get, beat.get))
      }
    }
  }

  private def parseData(defaults: Header)(data: String): Either[String, Note] = {
    val parseNote: State[String, String, Option[Int]] = {
      StringReader
        .readWhile { c =>
          (c >= 'A' && c <= 'G') ||
          (c >= 'a' && c <= 'g') ||
          (c == 'P' || c == 'p') ||
          (c == '#')
        }
        .flatMap {
          case "" | "p"   => State.pure(None)
          case "a"        => State.pure(Some(0))
          case "a#"       => State.pure(Some(1))
          case "b"        => State.pure(Some(2))
          case "c" | "b#" => State.pure(Some(3))
          case "c#"       => State.pure(Some(4))
          case "d"        => State.pure(Some(5))
          case "d#"       => State.pure(Some(6))
          case "e"        => State.pure(Some(7))
          case "f" | "e#" => State.pure(Some(8))
          case "f#"       => State.pure(Some(9))
          case "g"        => State.pure(Some(10))
          case "g#"       => State.pure(Some(11))
          case str        => State.error(s"Invalid note: $str")
        }
    }
    val isDotted = data.contains('.')
    (for {
      duration <- StringReader.readNumber
      note     <- parseNote
      octave   <- StringReader.readNumber
      baseDuration = 60.0 / (defaults.beat * duration.getOrElse(defaults.duration) / 4.0)
    } yield Note(
      octave.getOrElse(defaults.octave),
      note,
      if (isDotted) { baseDuration + baseDuration / 2 }
      else baseDuration
    )).run(data.filter(c => c != '.' && c != ' ')).map(_._2)
  }

  private def sequenceNotes(notes: Array[Either[String, Note]]): Either[String, AudioClip] = {
    notes
      .foldLeft[Either[String, AudioClip]](Right(AudioClip.empty)) {
        case (_, Left(error)) => Left(error)
        case (Left(error), _) => Left(error)
        case (Right(acc), Right(note)) =>
          Right(acc.append(oscilator.generateClip(note.duration, note.frequency)))
      }
  }

  final def loadClip(is: InputStream): Either[String, AudioClip] = {
    val bytes = fromInputStream(is)
    (for {
      jintu    <- readNextSection
      defaults <- readNextSection
      header   <- State.fromEither(parseHeader(jintu, defaults))
      data     <- readNextSection
      notes = data.split(",").map(parseData(header) _)
      clip <- State.fromEither(sequenceNotes(notes))
    } yield clip).run(bytes).map(_._2)
  }
}

object RtttlAudioReader {
  private case class Note(octave: Int, note: Option[Int], duration: Double) {
    def frequency: Double = note match {
      case None => 0.0
      case Some(n) =>
        val a1 = 55
        Math.pow(2, (octave - 1) + n / 12.0) * a1
    }
  }

  private object ByteStringOps {
    import ByteReader._
    private val separator = ':'.toInt

    val readNextSection: ParseState[String, String] =
      readWhile(char => char != separator)
        .map(chars => chars.map(_.toChar).mkString(""))
        .flatMap(str => skipBytes(1).map(_ => str))
  }

  private object StringReader {
    def readWhile(p: Char => Boolean): State[String, Nothing, String] = State { str =>
      val (prefix, suffix) = str.span(p)
      suffix -> prefix
    }

    val readNumber: State[String, Nothing, Option[Int]] =
      readWhile(c => c >= '0' && c <= '9').map { values =>
        if (values.nonEmpty) values.toIntOption
        else None
      }
  }
}
