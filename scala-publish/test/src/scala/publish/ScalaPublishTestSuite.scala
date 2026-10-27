package scala.publish

import munit.AnyFixture

import java.nio.file.Paths

import scala.concurrent.duration.*

trait ScalaPublishTestSuite extends munit.FunSuite {
  override def munitFlakyOK: Boolean  = ScalaPublishTestSuite.isCI
  override def munitTimeout: Duration = 2.minutes

  extension (munitContext: BeforeEach | AfterEach) {
    def locationFileName: String = {
      val location = munitContext match {
        case beforeEach: BeforeEach => beforeEach.test.location
        case afterEach: AfterEach   => afterEach.test.location
      }
      Paths.get(location.path).getFileName.toString.stripSuffix(".scala")
    }
  }

  val testStartEndLogger: Fixture[Unit] = new Fixture[Unit]("test-start-end-logger") {
    def apply(): Unit = ()

    override def beforeEach(context: BeforeEach): Unit =
      System.err.println(
        s">==== ${Console.CYAN}Running '${context.test.name}' from ${context.locationFileName}${Console.RESET}"
      )

    override def afterEach(context: AfterEach): Unit =
      System.err.println(
        s"X==== ${Console.CYAN}Finishing '${context.test.name}' from ${context.locationFileName}${Console.RESET}"
      )
  }

  override def munitFixtures: Seq[AnyFixture[?]] = List(testStartEndLogger)
}

object ScalaPublishTestSuite {
  val isCI: Boolean = System.getenv("CI") != null
}
