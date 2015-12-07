class Day01 extends Day {
  override def run(): Unit = {

    val instructions = getResourceAsString("/day01.txt").map(c => if(c.equals('(')) 1 else -1)

    def findIndex: Int = {
      var floor = 0
      var index = 0
      instructions.foreach(i => {
        index = index + 1
        floor = floor + i
        if(floor < 0) {
          return index
        }
      })

      -1
    }

    printAnswer(1, "One", instructions.sum)
    printAnswer(1, "Two", findIndex) //1797
  }
}
