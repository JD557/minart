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
final case class JsResource(resourcePath: String) extends Resource {
  def path = "./" + resourcePath

  private def loadFromLocalStorage(): Option[String] =
    Option(dom.window.localStorage.getItem(resourcePath))

  def unsafeInputStream(): InputStream = {
    val data = loadFromLocalStorage() match {
      case Some(d) => d
      case None =>
        val xhr = new XMLHttpRequest()
        xhr.open("GET", path, false)
        xhr.overrideMimeType("text/plain; charset=x-user-defined")
        xhr.send()
        if (xhr.status != 200) throw new Exception(s"Couldn't open resource: $resourcePath (${xhr.statusText})")
        xhr.responseText
    }
    new ByteArrayInputStream(data.toCharArray.map(_.toByte))
  }

  def unsafeOutputStream(): OutputStream = new OutputStream {
    val inner = new ByteArrayOutputStream()
    override def close(): Unit = {
      flush()
      inner.close()
    }
    override def flush(): Unit = {
      inner.flush()
      dom.window.localStorage.setItem(resourcePath, inner.toByteArray().iterator.map(_.toChar).mkString(""))
    }
    override def write(b: Array[Byte]): Unit                     = inner.write(b)
    override def write(b: Array[Byte], off: Int, len: Int): Unit = inner.write(b, off, len)
    override def write(b: Int): Unit                             = inner.write(b)
  }

  override def withSource[A](f: Source => A): Try[A] = Try {
    val data = loadFromLocalStorage() match {
      case Some(d) => d
      case None =>
        val xhr = new XMLHttpRequest()
        xhr.open("GET", path, false)
        xhr.send()
        if (xhr.status != 200) throw new Exception(s"Couldn't open resource: $resourcePath (${xhr.statusText})")
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
          if (xhr.status != 200)
            promise.failure(new Exception(s"Couldn't open resource: $resourcePath (${xhr.statusText})"))
          else promise.complete(Try(f(Source.fromString(xhr.responseText))))
        }
        xhr.send()
    }
    promise.future
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
          if (xhr.status != 200)
            promise.failure(new Exception(s"Couldn't open resource: $resourcePath (${xhr.statusText})"))
          else promise.complete(Try(f(new ByteArrayInputStream(xhr.responseText.toCharArray.map(_.toByte)))))
        }
        xhr.send()
    }
    promise.future
  }

}
