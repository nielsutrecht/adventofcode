object AdventOfCode {
  def main(args: Array[String]): Unit = {
    val list = List(new Day01)

    list.foreach(r => r.run())
  }
}
