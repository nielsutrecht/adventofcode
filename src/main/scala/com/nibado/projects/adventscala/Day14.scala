package com.nibado.projects.adventscala

class Day14 extends Day {
  val pattern = "(?<n>[A-Za-z]+) can fly (?<s>[0-9]+) km/s for (?<d>[0-9]+) seconds, but then must rest for (?<r>[0-9]+) seconds\\.".r
  override def run(): Unit = {
    val seconds = 2503
    val answer1 = getInput().map(r => r.simulate(seconds)).max

    printAnswer(14, "One", answer1)//2660

    val reindeer = getInput().map(r => (r, 0))

    printAnswer(14, "Two", answer1)//1256
  }

  def getInput(): List[Reindeer] = {
    getResource("/day14.txt").map(s => s match { case pattern(n, s, d, r) => new Reindeer(n, s.toInt, d.toInt, r.toInt)})
  }

  class Reindeer(val name: String, val speed: Int, val duration: Int, val rest: Int) {
    var distance: Int = 0
    var resting: Boolean = false
    var counter: Int = Integer.MIN_VALUE

    def tick {
      if (counter == Integer.MIN_VALUE) {
        resting = false
        counter = duration
      }
      counter -= 1
      distance = distance + (if (resting) 0 else speed)
      if (counter <= 0) {
        resting = !resting
        counter = if (resting) rest else duration
      }
    }

    def simulate(seconds: Int): Int = {
      for(i <- 0 to seconds) {
        tick
      }

      distance
    }
  }
}

object Test14 {
  def main(args: Array[String]) = {
    new Day14().run()
  }
}
