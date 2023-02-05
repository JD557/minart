package eu.joaocosta.minart.audio

import eu.joaocosta.minart.runtime.pure.{IOOps, RIO}

package object pure {

  /** Representation of a operation that requires an AudioPlayer, with the common Monad operations.
    *  This is the same as `RIO[AudioPlayer, A]`.
    */
  type AudioPlayerIO[+A] = RIO[AudioPlayer, A]

  /** Object containing the operations that act on an AudioPlayer.
    */
  object AudioPlayerIO extends AudioPlayerIOOps with IOOps[AudioPlayer]
}
