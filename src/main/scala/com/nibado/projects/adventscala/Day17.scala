package com.nibado.projects.adventscala

class Day17 extends Day {
  override def run(): Unit = {
    val containers = getResource("/day17.txt").map(s => s.toInt)

    val start = System.currentTimeMillis()
    println("Start: " + start)
    println(containers.permutations.size)
    println("Duration: " + (System.currentTimeMillis() - start))
  }
}

object Test17 {
  def main(args: Array[String]) = {
    new Day17().run()
  }
}