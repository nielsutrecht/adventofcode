package com.nibado.projects.adventscala

class Day11 extends Day {
  override def run(): Unit = {

  }

  def increment(password: Array[Char]) = {
    (password.length - 1 to 0 by -1).foreach(i => {
      val c = password(i) + 1.toChar


    })
  }
}

object Test {
  def main(argv: Array[String]) = {
    new Day11().run()
  }
}
