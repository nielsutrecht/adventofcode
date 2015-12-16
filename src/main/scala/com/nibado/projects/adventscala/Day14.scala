package com.nibado.projects.adventscala

class Day14 extends Day {
  val pattern = "(?<n>[A-Za-z]+) can fly (?<s>[0-9]+) km/s for (?<d>[0-9]+) seconds, but then must rest for (?<r>[0-9]+) seconds\\.".r
  override def run(): Unit = {
    printAnswer(14, "One", getInput.map(r => r.simulate(2503)).max)

    val reindeer = getInput

    for(i <- 0 to 2503) {
      reindeer.foreach(r =>  r.tick)

      val max = reindeer.map(r => r.distance).max
      reindeer.filter(r => r.distance == max).foreach(r => r.score = r.score + 1)
    }

    printAnswer(14, "Two", reindeer.map(r => r.score).max)
  }

  def getInput: List[Reindeer] = {
    getResource("/day14.txt").map(s => s match { case pattern(n, s, d, r) => new Reindeer(n, s.toInt, d.toInt, r.toInt)})
  }

  class Reindeer(val name: String, val speed: Int, val duration: Int, val rest: Int) {
    var distance: Int = 0
    var resting: Boolean = false
    var counter: Int = Integer.MIN_VALUE
    var score: Int = 0

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
