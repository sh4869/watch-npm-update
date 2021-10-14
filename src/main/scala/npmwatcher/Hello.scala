package npmwatcher

import cats.effect._
import cats.effect.implicits._
import cats.effect.std.Queue
import cats.syntax.all._
import com.typesafe.scalalogging.LazyLogging
import fs2.Stream
import fs2.concurrent.Topic
import fs2.io.file.Files
import io.circe.Json
import io.circe.generic.auto._
import io.circe.parser.decode
import org.http4s._
import org.http4s.client.blaze._
import org.http4s.implicits._
import org.typelevel.jawn.fs2._

import java.nio.file.Path
import java.time.LocalDateTime
import java.time.ZonedDateTime
import scala.concurrent.ExecutionContext.global

object Hello extends IOApp with LazyLogging {

  implicit val f = io.circe.jawn.CirceSupportParser.facade
  val lineBreak: Char = "\n".charAt(0)
  def run(args: List[String]): IO[ExitCode] = {
    val req = Request[IO](
      Method.GET,
      uri"https://replicate.npmjs.com/_changes?include_docs=true&feed=continuous"
    )
    BlazeClientBuilder[IO](global).resource.use { client =>
      client
        .stream(req)
        .flatMap(
          _.body
            .split(_.toChar == lineBreak)
            .parseJsonStream
            .map(v => v.as[NpmPackageInfo])
            .collect[NpmPackageInfo] { case Right(r) => r }
        )
        .map(v => logger.info(v.doc.name))
        .compile
        .drain
        .as(ExitCode.Success)
    }
  }
}

case class Config(
    lastUpdated: ZonedDateTime
)

case class NpmPackage(
    name: String,
    version: String,
    dependencies: Map[String, String]
)

case class UpdatePackage(
    updateTime: ZonedDateTime,
    pack: NpmPackage
)

object StreamSubsriber {
  def newPackgePublisher[F[_]](
      info: Config,
      source: Stream[F, NpmPackageInfo]
  ): Topic[F, NpmPackageInfo] = ???
}

class NewPackagePublisher[F[_]: Async: Files](
    config: Ref[F, Config],
    configPath: Path
) {
  def newPackageTopic(
      topic: Topic[F, NpmPackageInfo],
      source: Stream[F, NpmPackageInfo]
  ) = topic.publish(source.evalFilter { pack =>
    config.get.map { conf =>
      pack.doc.time.values.exists(datestr =>
        ZonedDateTime.parse(datestr).isAfter(conf.lastUpdated)
      )
    }
  })

  private def updateConfigInternal(topic: Topic[F, UpdatePackage]) =
    topic.subscribe(1).evalMap(time => config.set(Config(time.updateTime)))

  def updateConfigFile(topic: Topic[F, UpdatePackage]) = topic
    .subscribe(10)
    .map(v => v.updateTime.toString().toByte)
    .through(Files[F].writeAll(configPath))

}
