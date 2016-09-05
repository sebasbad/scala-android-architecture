package commons

object AppLog {

  val tag = "scala_architecture"

  def printErrorMessage(ex: Throwable, message: Option[String] = None) = {
    try {
      val outputEx = Option(ex.getCause) getOrElse ex
      println(s"$tag - ${message getOrElse errorMessage(outputEx)} $outputEx")
    } catch { case _: Throwable => }
  }

  def printErrorTaskMessage(header: String, ex: Throwable) = {
    try {
      println(s"$tag - $header")
      printErrorMessage(ex, Some(errorMessage(ex)))
    } catch { case _: Throwable => }
  }

  private[this] def errorMessage(ex: Throwable): String =
    Option(ex.getMessage) getOrElse ex.getClass.toString

}
