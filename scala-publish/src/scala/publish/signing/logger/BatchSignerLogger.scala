package scala.publish.signing.logger

import java.io.PrintStream

import scala.publish.fileset.Path

final class BatchSignerLogger(out: PrintStream, verbosity: Int) extends SignerLogger {
  override def signingElement(id: Object, path: Path): Unit =
    if verbosity >= 0 then out.println(s"Signing ${path.repr}")
  override def signedElement(id: Object, path: Path, excOpt: Option[Throwable]): Unit =
    if verbosity >= 0 then out.println(s"Signed ${path.repr}")
}
