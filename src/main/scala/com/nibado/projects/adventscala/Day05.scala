package com.nibado.projects.adventscala

import scala.collection.immutable.HashSet

class Day05 extends Day {
  val vowels = HashSet('a', 'e', 'i', 'o', 'u')
  val bad = List("ab", "cd", "pq", "xy")

  override def run(): Unit = {
    val input = getResource("/day05.txt")

    val result1 = input
      .filter(s => vowelCheck(s))
      .filter(s => doubleCheck(s, 1))
      .count(s => badCheck(s))

    printAnswer(5, "One", result1)

    val result2 = input
      .filter(s => doubleCheck(s, 2))
      .count(s => pairCheck(s))

    printAnswer(5, "Two", result2)
  }

  def vowelCheck(line: String): Boolean = {
    line.count(c => vowels.contains(c)) >= 3
  }

  def doubleCheck(line: String, distance: Int): Boolean = {
    line.sliding(1 + distance).count(s => s(0) == s(distance)) > 0
  }

  def badCheck(line: String): Boolean = {
    bad.foreach(s => {
      if(line.contains(s)) {
        return false
      }
    })

    true
  }

  def pairCheck(line: String): Boolean = {
    line.sliding(2).exists(s =>  line.lastIndexOf(s) > line.indexOf(s) + 1)
  }
}
