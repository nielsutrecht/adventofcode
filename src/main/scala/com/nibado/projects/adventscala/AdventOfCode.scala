package com.nibado.projects.adventscala

object AdventOfCode {
  def main(args: Array[String]): Unit = {
    val list = List(new Day01, new Day02, new Day03, new Day04, new Day05)

    list.foreach(r => r.run())
  }
}
