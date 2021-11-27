package eu.joaocosta.minart.backend

import java.io.{FileInputStream, InputStream}

import scala.concurrent.Future
import scala.io.Source
import scala.util.Try

import eu.joaocosta.minart.runtime.{Resource, ResourceLoader}

/** Resource loader that fetches the data from a file.
  *
  *  Due to scala-native limitations, the async methods are actually synchronous.
  */
object NativeResourceLoader extends ResourceLoader {
  def createResource(resourcePath: String): Resource = new Resource {
    def path                                      = "./" + resourcePath
    def asSource(): Source                        = Source.fromFile(path)
    def asSourceAsync(): Future[Source]           = Future.fromTry(Try(asSource()))
    def asInputStream(): InputStream              = new FileInputStream(path)
    def asInputStreamAsync(): Future[InputStream] = Future.fromTry(Try(asInputStream()))
  }
}
