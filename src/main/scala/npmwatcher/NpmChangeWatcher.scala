package npmwatcher

class NpmChangeWatcher(since: Int) {

  def buildUrl() = {
    s"${NpmChangeWatcher.URL}?"
  }
}

object NpmChangeWatcher {
  val URL = "https://replicate.npmjs.com/_changes"
}
