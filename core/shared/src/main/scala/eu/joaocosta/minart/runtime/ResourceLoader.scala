package eu.joaocosta.minart.runtime

import scala.io.Source

import eu.joaocosta.minart.backend.defaults.DefaultBackend

/** A platform agnostic way of loading resources.
  * Depending on the backend, such resources might be load from the binary, from a local file or from the network.
  */
trait ResourceLoader {

  /** Creates a new resource object pointing to a specific path.
    *
    * For best results, the path should not start with `./` or `/`
    * (the loader should be responsible to decide if the path is relative
    * or absolute)
    *
    * @param resourcePath Resource path
    */
  def createResource(resourcePath: String): Resource
}

object ResourceLoader {
  def apply(): ResourceLoader =
    DefaultBackend[Any, ResourceLoader].defaultValue(())
}
