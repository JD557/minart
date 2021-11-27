package eu.joaocosta.minart.backend

import java.io.{FileInputStream, InputStream}

import scala.concurrent.Future
import scala.io.Source
import scala.util.{Try, Using}

import eu.joaocosta.minart.runtime.{Resource, ResourceLoader}

/** Resource loader that fetches the data from a file.
  *
  *  Due to scala-native limitations, the async methods are actually synchronous.
  */
object NativeResourceLoader extends ResourceLoader {
  def createResource(resourcePath: String): Resource = new Resource {
    def path                                  = "./" + resourcePath
    def withSource[A](f: Source => A): Try[A] = Using(Source.fromFile(path))(f)
    def withSourceAsync[A](f: Source => A): Future[A] =
      Future.fromTry(withSource(f))
    def withInputStream[A](f: InputStream => A): Try[A] = Using(new FileInputStream(path))(f)
    def withInputStreamAsync[A](f: InputStream => A): Future[A] =
      Future.fromTry(withInputStream(f))
  }
}
