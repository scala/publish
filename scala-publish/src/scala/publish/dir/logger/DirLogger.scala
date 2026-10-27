package scala.publish.dir.logger

import java.nio.file.Path

trait DirLogger {
  // dir should be removed…
  def reading(dir: Path): Unit             = ()
  def element(dir: Path, file: Path): Unit = ()
  def read(dir: Path, elements: Int): Unit = ()

  def start(): Unit                    = ()
  def stop(keep: Boolean = true): Unit = ()
}
