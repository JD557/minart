package eu.joaocosta.minart.graphics.image.qoi

private[qoi] sealed trait Op
private[qoi] object Op {
  final case class OpRgb(red: Int, green: Int, blue: Int)              extends Op
  final case class OpRgba(red: Int, green: Int, blue: Int, alpha: Int) extends Op
  final case class OpIndex(index: Int)                                 extends Op
  final case class OpDiff(dr: Int, dg: Int, db: Int)                   extends Op
  final case class OpLuma(dg: Int, drdg: Int, dbdg: Int) extends Op {
    val dr = drdg + dg
    val db = dbdg + dg
  }
  final case class OpRun(run: Int) extends Op
}
