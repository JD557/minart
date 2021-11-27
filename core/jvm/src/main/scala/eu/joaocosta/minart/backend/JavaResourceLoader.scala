package eu.joaocosta.minart.backend

import java.io.{FileInputStream, InputStream}

import scala.concurrent._
import scala.io.Source
import scala.util.Try

import eu.joaocosta.minart.runtime.{Resource, ResourceLoader}

/** Resource loader by first trying to access the jar's resources.
  * If that fail, it tries to fetch the data from a file.
  */
object JavaResourceLoader extends ResourceLoader {
  def createResource(resourcePath: String): Resource = {
    implicit val ec: ExecutionContext = ExecutionContext.global
    new Resource {
      def path = "./" + resourcePath
      def asSource(): Source =
        Try(Source.fromResource(resourcePath))
          .getOrElse(Source.fromFile(path))
      def withSourceAsync[A](f: Source => A): Future[A] = Future(f(blocking(asSource())))
      def asInputStream(): InputStream =
        Try(Option(this.getClass().getResourceAsStream("/" + resourcePath)).get)
          .getOrElse(new FileInputStream(path))
      def withInputStreamAsync[A](f: InputStream => A): Future[A] = Future(f(blocking(asInputStream())))
    }
  }
}
