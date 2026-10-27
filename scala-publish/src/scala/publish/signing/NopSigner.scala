package scala.publish.signing

import java.time.Instant

import scala.publish.Content
import scala.publish.fileset.{FileSet, Path}
import scala.publish.signing.logger.SignerLogger

object NopSigner extends Signer {
  def sign(content: Content): Either[String, String] =
    Right("")

  override def signatures(
    fileSet: FileSet,
    now: Instant,
    dontSignExtensions: Set[String],
    dontSignFiles: Set[String],
    logger: => SignerLogger
  ): Either[(Path, Content, String), FileSet] =
    Right(FileSet.empty)
}
