package eu.joaocosta.minart.audio.sound.qoa

final case class QoaState(history: Vector[Short] = Vector(0, 0, 0, 0), weights: Vector[Short] = Vector(0, 0, 0, 0)) {
  lazy val prediction: Int = history.zip(weights).map { case (h, w) => h.toInt * w.toInt }.sum >> 13
  def update(sample: Short, residual: Int): QoaState = {
    val delta = residual >> 4
    val newWeights = history.zip(weights).map { case (h, w) =>
      if (h < 0) (w - delta).toShort else (w + delta).toShort
    }
    val newHistory = (history :+ sample).tail
    QoaState(newHistory, newWeights)
  }
}
