package eu.joaocosta.minart.audio.sound.rtttl

import java.io.InputStream

import scala.annotation.tailrec
import scala.io.Source

import eu.joaocosta.minart.audio._
import eu.joaocosta.minart.audio.sound._

/** Audio reader for RTTTL files.
  */
trait RtttlAudioReader extends AudioClipReader {

  def oscilator: Oscilator

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

  case class Note(octave: Double, note: Option[Int], duration: Double) {
    def frequency: Double = note match {
      case None => 0.0
      case Some(n) =>
        val a1 = 55
        math.pow(2, (octave - 1) + n / 12.0) * 55
    }
  }

  def parseData(defaults: Header)(data: String): Either[String, Note] = {
    def parseNumber(str: String): (String, Option[Int]) = {
      val (prefix, suffix) = str.span(c => c >= '0' && c <= '9')
      (suffix, prefix.toIntOption)
    }
    def parseNote(str: String): Either[String, (String, Option[Int])] = {
      val (prefix, suffix) = str.span(c =>
        (c >= 'A' && c <= 'G') ||
          (c >= 'a' && c <= 'g') ||
          (c == 'P' || c == 'p') ||
          (c == '#')
      )
      prefix match {
        case ""   => Left("Failed to parse note, no valid characters found")
        case "p"  => Right((suffix, None))
        case "a"  => Right((suffix, Some(0)))
        case "a#" => Right((suffix, Some(1)))
        case "b"  => Right((suffix, Some(2)))
        case "c"  => Right((suffix, Some(3)))
        case "c#" => Right((suffix, Some(4)))
        case "d"  => Right((suffix, Some(5)))
        case "d#" => Right((suffix, Some(6)))
        case "e"  => Right((suffix, Some(7)))
        case "f"  => Right((suffix, Some(8)))
        case "f#" => Right((suffix, Some(9)))
        case "g"  => Right((suffix, Some(10)))
        case "g#" => Right((suffix, Some(11)))
        case _    => Left(s"Invalid note: $prefix")
      }
    }

    val (str1, duration) = parseNumber(data)
    parseNote(str1).map { case (str2, note) =>
      val (str3, octave) = parseNumber(str2)
      val isDotted       = str3 == "."
      val baseDuration   = 60.0 / (defaults.beat * duration.getOrElse(defaults.duration) / 4.0)
      Note(
        octave.getOrElse(defaults.octave),
        note,
        if (isDotted) { baseDuration + baseDuration / 2 }
        else baseDuration
      )
    }
  }

  def loadClip(is: InputStream): Either[String, AudioClip] = {
    // RTTTL files are small enough to fit in memory
    val sections = Source.fromInputStream(is).getLines.mkString("").split(":")
    if (sections.length != 3) Left(s"RTTTL file had ${sections.length} sections. Expected 3.")
    else {
      parseHeader(sections(0), sections(1)).flatMap { header =>
        val notes = sections(2).split(",").map(parseData(header) _)
        notes.foldLeft[Either[String, AudioClip]](Right(AudioClip.empty)) {
          case (_, Left(error)) => Left(error)
          case (Left(error), _) => Left(error)
          case (Right(acc), Right(note)) =>
            Right(acc.append(oscilator(note.frequency).clip(note.duration)))
        }
      }
    }

  }
}
