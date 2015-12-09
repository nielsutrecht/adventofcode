package com.nibado.projects.adventscala

class Day09 extends Day {
  val pattern = "([A-Za-z]+) to ([A-Za-z]+) = ([0-9]+)".r

  override def run(): Unit = {
    val distanceMap = getResource("/day09.txt").map { case pattern(from, to, amount) => (from, to) -> amount.toInt}.toMap

    val permutations = distanceMap.keys.flatMap(s => List(s._1, s._2)).toSet.toList.permutations

    val results = permutations.map(perm => {
      perm.sliding(2, 1).map(l => (l(0),l(1))).map(names => distanceMap.getOrElse(names, distanceMap(names.swap))).sum
    }).toSet

    printAnswer(9, "One", results.min)
    printAnswer(9, "Two", results.max)
  }
}