package npmwatcher

import io.circe._
import io.circe.generic.semiauto._

case class NpmVersionInfo(
    version: String,
    dependencies: Map[String, String],
    optionalDependencies: Map[String, String]
)

case class NpmPackageInfoContent(
    name: String,
    versions: Map[String, NpmVersionInfo],
    time: Map[String, String]
)


case class NpmPackageInfo(
    doc: NpmPackageInfoContent,
    seq: Int,
)

object NpmPackageInfo {

}
