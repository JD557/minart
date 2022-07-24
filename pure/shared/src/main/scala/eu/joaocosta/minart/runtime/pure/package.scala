package eu.joaocosta.minart.runtime

package object pure {

  /** Representation of a operation on that requires a Resource, with the common Monad operations.
    *  This is the same as `RIO[Resource, A]`.
    */
  type ResourceIO[+A] = RIO[Resource, A]

  /** Object containing the operations that act on a Resource.
    */
  object ResourceIO extends ResourceIOOps with IOOps[Resource]
}
