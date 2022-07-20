package eu.joaocosta.minart.backend

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, InputStream, OutputStream}

import scala.concurrent.{Future, Promise}
import scala.io.Source
import scala.scalajs.js
import scala.util.{Try, Using}

import org.scalajs.dom
import org.scalajs.dom.{ProgressEvent, XMLHttpRequest}

import eu.joaocosta.minart.runtime.Resource

/** Resource loader that fetches resources using a XML HTTP Request.
  */
final case class JsResource(resourcePath: String) extends Resource {
  def path = "./" + resourcePath

  private def loadFromLocalStorage(): Option[String] =
    Option(dom.window.localStorage.getItem(resourcePath))

  def withSource[A](f: Source => A): Try[A] = Try {
    val data = loadFromLocalStorage() match {
      case Some(d) => d
      case None =>
        val xhr = new XMLHttpRequest()
        xhr.open("GET", path, false)
        xhr.send()
        xhr.responseText
    }
    f(Source.fromString(data))
  }

  def withSourceAsync[A](f: Source => A): Future[A] = {
    val promise = Promise[A]()
    loadFromLocalStorage() match {
      case Some(data) =>
        promise.complete(Try(f(Source.fromString(data))))
      case None =>
        val xhr = new XMLHttpRequest()
        xhr.open("GET", path)
        xhr.onloadend = (event: ProgressEvent) => {
          if (xhr.status != 200) promise.failure(new Exception(xhr.statusText))
          else promise.complete(Try(f(Source.fromString(xhr.responseText))))
        }
        xhr.send()
    }
    promise.future
  }

  def withInputStream[A](f: InputStream => A): Try[A] = Try {
    f(unsafeInputStream())
  }

  def withInputStreamAsync[A](f: InputStream => A): Future[A] = {
    val promise = Promise[A]()
    loadFromLocalStorage() match {
      case Some(data) =>
        val is = new ByteArrayInputStream(data.toCharArray.map(_.toByte))
        promise.complete(Try(f(is)))
      case None =>
        val xhr = new XMLHttpRequest()
        xhr.open("GET", path)
        xhr.overrideMimeType("text/plain; charset=x-user-defined")
        xhr.onloadend = (event: ProgressEvent) => {
          if (xhr.status != 200) promise.failure(new Exception(xhr.statusText))
          else promise.complete(Try(f(new ByteArrayInputStream(xhr.responseText.toCharArray.map(_.toByte)))))
        }
        xhr.send()
    }
    promise.future
  }

  def unsafeInputStream(): InputStream = {
    val data = loadFromLocalStorage() match {
      case Some(d) => d
      case None =>
        val xhr = new XMLHttpRequest()
        xhr.open("GET", path, false)
        xhr.overrideMimeType("text/plain; charset=x-user-defined")
        xhr.send()
        xhr.responseText
    }
    new ByteArrayInputStream(data.toCharArray.map(_.toByte))
  }

  def withOutputStream(f: OutputStream => Unit): Unit =
    Using[OutputStream, Unit](new ByteArrayOutputStream()) { os =>
      f(os)
      dom.window.localStorage.setItem(resourcePath, os.toString())
    }
}
