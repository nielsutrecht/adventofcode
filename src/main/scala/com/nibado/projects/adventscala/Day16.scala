package com.nibado.projects.adventscala

class Day16 extends Day {
  val patternLine = "Sue ([0-9]+): ([a-z0-9:, ]+)".r
  val patternProperty = "([a-z]+): ([0-9]+)".r

  override def run(): Unit = {
    val sues = getResource("/day16.txt")
      .map(l => l match { case patternLine(num, props) => (num.toInt, props) })
      .map(t => new Sue(t._1, patternProperty.findAllMatchIn(t._2)
        .map(m => m match { case patternProperty(ing, amount) => (ing, amount.toInt)}).toMap)
      )

    val target = Sue(0, Map("children" -> 3,"cats" -> 7,"samoyeds" -> 2,"pomeranians" -> 3,"akitas" -> 0,"vizslas" -> 0,"goldfish" -> 5,"trees" -> 3,"cars" -> 2,"perfumes" -> 1))

    printAnswer(16, "One", sues.find(s => isMatch(s, target, Map.empty)).get.num)

    val gt = (a: Int, b: Int) => a > b
    val lt = (a: Int, b: Int) => a < b

    printAnswer(16, "Two", sues.find(s => isMatch(s, target, Map("cats" -> gt, "trees" -> gt, "pomeranians" -> lt, "goldfish" -> lt))).get.num)
  }

  def isMatch(sue1: Sue, sue2: Sue, operands: Map[String, (Int, Int) => Boolean]): Boolean = {
    val keys = sue1.properties.keySet ++ sue2.properties.keySet

    keys.map(k => (sue1.properties.getOrElse(k, -1), sue2.properties.getOrElse(k, -1), operands.getOrElse(k, (a: Int, b: Int) => a == b)))
      .count(t => t._1 == -1 || t._2 == -1 || t._3(t._1, t._2)) == keys.size
  }

  case class Sue(num: Int, properties: Map[String, Int])
}
