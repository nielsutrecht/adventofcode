package com.nibado.projects.adventscala

/**
  * Created by nielsdommerholt on 07/12/15.
  */
object AdventOfCode {
  def main(args: Array[String]): Unit = {
    val list = List(new Day01, new Day02)

    list.foreach(r => r.run())
  }
}
