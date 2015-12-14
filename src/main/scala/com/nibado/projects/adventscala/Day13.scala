package com.nibado.projects.adventscala

class Day13 extends Day {
  val pattern = "(?<n1>[A-Za-z]+) would (?<lg>lose|gain) (?<h>[0-9]+) happiness units by sitting next to (?<n2>[A-Za-z]+)\\.".r
  override def run(): Unit = {
    val input = getResource("/day13.txt").map(s => s match { case pattern(n1, lg, h, n2) => (n1, n2) -> h.toInt * (if(lg.equals("lose")) -1 else 1)}).toMap
    val names = input.keys.flatMap(s => List(s._1, s._2)).toSet.toList

    def happiness(pair: List[String]):Int = {
      input.getOrElse((pair.head, pair.last), 0) + input.getOrElse((pair.last, pair.head), 0)
    }

    def solve(list: List[String]) : Int = {
      list.permutations.map(l => {
        l.sliding(2).map(s => happiness(s)).sum + happiness(List(l.head, l.last))
      }).max
    }

    printAnswer(13, "One", solve(names))
    printAnswer(13, "Two", solve("FooBob" :: names))
  }
}
