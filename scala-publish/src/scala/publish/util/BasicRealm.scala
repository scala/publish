package scala.publish.util

import java.util.Locale
import java.util.regex.Pattern

// Copied from coursier.cache.CacheUrl.BasicRealm, which is private[coursier]
private[publish] object BasicRealm {
  private val BasicAuthBase = (
    "(?i)" + // case-insensitive, the response might be BASIC realm=
      "^" +
      Pattern.quote("Basic") +
      " +(.*)" + // skip spaces and then take everything
      "$"
  ).r

  private val Param = (
    "\\s*(\\S+?)" + Pattern.quote("=\"") +
      "([^" + Pattern.quote("\"") + "]*)" +
      Pattern.quote("\"") +
      "\\s*" +  // skip any trailing spaces
      "(?:,|$)" // either we're at the end or we have a trailing comma
  ).r

  /* Extracting the realm from lines such as:

  Basic realm="Sonatype Nexus Repository Manager"
  Basic realm="SomeRealm", charset="UTF-8"
  BASIC charset="UTF-8", realm="SomeRealm"

  see https://tools.ietf.org/html/rfc7617#section-2.1
   */
  def unapply(wwwAuthenticate: String): Option[String] =
    wwwAuthenticate match {
      case BasicAuthBase(basicAuthLine) =>
        Param.findAllMatchIn(basicAuthLine)
          .map(mobj => mobj.group(1).toLowerCase(Locale.ROOT) -> mobj.group(2))
          .collectFirst { case ("realm", realm) => realm }
      case _ =>
        None
    }
}
