package eu.joaocosta.minart.backend

import java.io.{FileInputStream, InputStream}

import scala.concurrent._
import scala.io.Source
import scala.util.{Try, Using}

import eu.joaocosta.minart.runtime.{Resource, ResourceLoader}

/** Resource loader by first trying to access the jar's resources.
  * If that fail, it tries to fetch the data from a file.
  */
object JavaResourceLoader extends ResourceLoader {
  def createResource(resourcePath: String): Resource = {
    implicit val ec: ExecutionContext = ExecutionContext.global
    new Resource {
      def path = "./" + resourcePath
      def withSource[A](f: Source => A): Try[A] = {
        Using(Try(Source.fromResource(resourcePath)).getOrElse(Source.fromFile(path)))(f)
      }
      def withSourceAsync[A](f: Source => A): Future[A] = Future(blocking(withSource(f)).get)
      def withInputStream[A](f: InputStream => A): Try[A] =
        Using(
          Try(Option(this.getClass().getResourceAsStream("/" + resourcePath)).get).getOrElse(new FileInputStream(path))
        )(f)
      def withInputStreamAsync[A](f: InputStream => A): Future[A] = Future(blocking(withInputStream(f)).get)
    }
  }
}
