package com.nibado.projects.adventscala

object AdventOfCode {
  def main(args: Array[String]): Unit = {
    val list = List(new Day01, new Day02, new Day03, new Day04, new Day05, new Day06, new Day07, new Day08, new Day09, new Day10)

    list.foreach(r => r.run())
  }
}
