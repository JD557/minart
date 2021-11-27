package eu.joaocosta.minart.backend

import java.io.{ByteArrayInputStream, InputStream}

import scala.concurrent.{Future, Promise}
import scala.io.Source
import scala.scalajs.js

import org.scalajs.dom.{ProgressEvent, XMLHttpRequest}

import eu.joaocosta.minart.runtime.{Resource, ResourceLoader}

/** Resource loader that fetches resources using a XML HTTP Request.
  */
object JsResourceLoader extends ResourceLoader {
  def createResource(resourcePath: String): Resource = new Resource {
    def path = "./" + resourcePath

    def asSource(): Source = {
      val xhr = new XMLHttpRequest()
      xhr.open("GET", path, false)
      xhr.send()
      Source.fromString(xhr.responseText)
    }

    def asSourceAsync(): Future[Source] = {
      val promise = Promise[Source]()
      val xhr     = new XMLHttpRequest()
      xhr.open("GET", path)
      xhr.onloadend = (event: ProgressEvent) => {
        if (xhr.status != 200) promise.failure(new Exception(xhr.statusText))
        else promise.success(Source.fromString(xhr.responseText))
      }
      xhr.send()
      promise.future
    }

    def asInputStream(): InputStream = {
      val xhr = new XMLHttpRequest()
      xhr.open("GET", path, false)
      xhr.overrideMimeType("text/plain; charset=x-user-defined")
      xhr.send()
      new ByteArrayInputStream(xhr.responseText.toCharArray.map(_.toByte));
    }

    def asInputStreamAsync(): Future[InputStream] = {
      val promise = Promise[InputStream]()
      val xhr     = new XMLHttpRequest()
      xhr.open("GET", path)
      xhr.overrideMimeType("text/plain; charset=x-user-defined")
      xhr.onloadend = (event: ProgressEvent) => {
        if (xhr.status != 200) promise.failure(new Exception(xhr.statusText))
        else promise.success(new ByteArrayInputStream(xhr.responseText.toCharArray.map(_.toByte)))
      }
      xhr.send()
      promise.future
    }
  }
}
