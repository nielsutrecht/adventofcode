package com.nibado.projects.adventscala

class Day10 extends Day {
  override def run(): Unit = {
    val input = getResourceAsString("/day10.txt")
    transform(40, input)
    transform(50, input)
  }

  def transform(times: Int, input: String): Unit = {
    var current = input;
    (1 to times).foreach(n => current = transform(current))
    printAnswer(10, if(times == 40) "One" else "Two", current.length)
  }

  def transform(input: String) : String = {
    val it = input.iterator
    val build = new StringBuilder
    val output = new StringBuilder
    while(it.hasNext) {
      val c = it.next()
      if(build.nonEmpty && build.charAt(0) != c) {
        output.append(build.length.toString).append(build.charAt(0))
        build.setLength(0)
      }
      build += c
    }

    output.append(build.length.toString).append(build.charAt(0)).toString()
  }
}
