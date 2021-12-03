package eu.joaocosta.minart.runtime

import eu.joaocosta.minart.runtime.pure.{IOOps, RIO}

package object pure {
  type ResourceIO[+A] = RIO[Resource, A]
  object ResourceIO extends ResourceIOOps with IOOps[Resource]
}
