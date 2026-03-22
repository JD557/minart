package eu.joaocosta.minart.backend

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, InputStream, OutputStream}

import scala.concurrent.{Future, Promise}
import scala.io.Source
import scala.util.Try

import org.scalajs.dom
import org.scalajs.dom.{ProgressEvent, XMLHttpRequest}

import eu.joaocosta.minart.runtime.Resource

/** Resource loader that fetches resources from the local storage.
  * If it fails, it uses a XML HTTP Request.
  */
final case class JsResource(resourcePath: Option[String]) extends Resource {

  def path = resourcePath.map(p => s"./$p")

  private def loadFromLocalStorage(): Option[String] =
    resourcePath.flatMap(p => Option(dom.window.localStorage.getItem(p)))

  def unsafeInputStream(): InputStream = {
    resourcePath match {
      case Some(p) =>
        val data = loadFromLocalStorage() match {
          case Some(d) => d
          case None    =>
            val xhr = new XMLHttpRequest()
            xhr.open("GET", s"./$p", false)
            xhr.overrideMimeType("text/plain; charset=x-user-defined")
            xhr.send()
            if (xhr.status != 200) throw new Exception(s"Couldn't open resource: $resourcePath (${xhr.statusText})")
            xhr.responseText
        }
        new ByteArrayInputStream(data.toCharArray.map(_.toByte))
      case None =>
        // Scala.js doesn't support System.in
        InputStream.nullInputStream()
    }
  }

  def unsafeOutputStream(): OutputStream =
    resourcePath match {
      case Some(p) =>
        new OutputStream {
          val inner                  = new ByteArrayOutputStream()
          override def close(): Unit = {
            flush()
            inner.close()
          }
          override def flush(): Unit = {
            inner.flush()
            dom.window.localStorage.setItem(p, inner.toByteArray().iterator.map(_.toChar).mkString(""))
          }
          override def write(b: Array[Byte]): Unit                     = inner.write(b)
          override def write(b: Array[Byte], off: Int, len: Int): Unit = inner.write(b, off, len)
          override def write(b: Int): Unit                             = inner.write(b)
        }
      case None => System.out
    }

  override def withSource[A](f: Source => A): Try[A] = resourcePath match {
    case Some(p) =>
      Try {
        val data = loadFromLocalStorage() match {
          case Some(d) => d
          case None    =>
            val xhr = new XMLHttpRequest()
            xhr.open("GET", s"./$p", false)
            xhr.send()
            if (xhr.status != 200) throw new Exception(s"Couldn't open resource: $resourcePath (${xhr.statusText})")
            xhr.responseText
        }
        f(Source.fromString(data))
      }
    case None =>
      // Stdin is not supported in Scala.js
      Try(f(Source.fromString("")))
  }

  def withSourceAsync[A](f: Source => A): Future[A] = resourcePath match {
    case Some(p) =>
      val promise = Promise[A]()
      loadFromLocalStorage() match {
        case Some(data) =>
          promise.complete(Try(f(Source.fromString(data))))
        case None =>
          val xhr = new XMLHttpRequest()
          xhr.open("GET", s"./$p")
          xhr.onloadend = (_: ProgressEvent) => {
            if (xhr.status != 200)
              promise.failure(new Exception(s"Couldn't open resource: $resourcePath (${xhr.statusText})"))
            else promise.complete(Try(f(Source.fromString(xhr.responseText))))
          }
          xhr.send()
      }
      promise.future
    case None =>
      // Stdin is not supported in Scala.js
      Future.fromTry(withSource(f))
  }

  def withInputStreamAsync[A](f: InputStream => A): Future[A] = resourcePath match {
    case Some(p) =>
      val promise = Promise[A]()
      loadFromLocalStorage() match {
        case Some(data) =>
          val is = new ByteArrayInputStream(data.toCharArray.map(_.toByte))
          promise.complete(Try(f(is)))
        case None =>
          val xhr = new XMLHttpRequest()
          xhr.open("GET", s"./$p")
          xhr.overrideMimeType("text/plain; charset=x-user-defined")
          xhr.onloadend = (_: ProgressEvent) => {
            if (xhr.status != 200)
              promise.failure(new Exception(s"Couldn't open resource: $resourcePath (${xhr.statusText})"))
            else promise.complete(Try(f(new ByteArrayInputStream(xhr.responseText.toCharArray.map(_.toByte)))))
          }
          xhr.send()
      }
      promise.future
    case None =>
      // Stdin is not supported in Scala.js
      Future.fromTry(withInputStream(f))
  }

}
