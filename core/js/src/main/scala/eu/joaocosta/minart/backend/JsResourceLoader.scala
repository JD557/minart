package eu.joaocosta.minart.backend

import java.io.{ByteArrayInputStream, InputStream}

import scala.concurrent.{Future, Promise}
import scala.io.Source
import scala.scalajs.js
import scala.util.Try

import org.scalajs.dom.{ProgressEvent, XMLHttpRequest}

import eu.joaocosta.minart.runtime.{Resource, ResourceLoader}

/** Resource loader that fetches resources using a XML HTTP Request.
  */
object JsResourceLoader extends ResourceLoader {
  def createResource(resourcePath: String): Resource = new Resource {
    def path = "./" + resourcePath

    def withSource[A](f: Source => A): Try[A] = Try {
      val xhr = new XMLHttpRequest()
      xhr.open("GET", path, false)
      xhr.send()
      f(Source.fromString(xhr.responseText))
    }

    def withSourceAsync[A](f: Source => A): Future[A] = {
      val promise = Promise[A]()
      val xhr     = new XMLHttpRequest()
      xhr.open("GET", path)
      xhr.onloadend = (event: ProgressEvent) => {
        if (xhr.status != 200) promise.failure(new Exception(xhr.statusText))
        else promise.complete(Try(f(Source.fromString(xhr.responseText))))
      }
      xhr.send()
      promise.future
    }

    def withInputStream[A](f: InputStream => A): Try[A] = Try {
      val xhr = new XMLHttpRequest()
      xhr.open("GET", path, false)
      xhr.overrideMimeType("text/plain; charset=x-user-defined")
      xhr.send()
      f(new ByteArrayInputStream(xhr.responseText.toCharArray.map(_.toByte)))
    }

    def withInputStreamAsync[A](f: InputStream => A): Future[A] = {
      val promise = Promise[A]()
      val xhr     = new XMLHttpRequest()
      xhr.open("GET", path)
      xhr.overrideMimeType("text/plain; charset=x-user-defined")
      xhr.onloadend = (event: ProgressEvent) => {
        if (xhr.status != 200) promise.failure(new Exception(xhr.statusText))
        else promise.complete(Try(f(new ByteArrayInputStream(xhr.responseText.toCharArray.map(_.toByte)))))
      }
      xhr.send()
      promise.future
    }
  }
}
