package com.nibado.projects.adventscala

trait Day extends Runnable {
  def getResourceAsString(file: String): String = {
    scala.io.Source.fromInputStream(getClass.getResourceAsStream(file)).mkString
  }

  def getResource(file: String): List[String] = {
    scala.io.Source.fromInputStream(getClass.getResourceAsStream(file)).getLines.toList
  }

  def printAnswer(day: Int, section: String, result: Any): Unit = {
    printf("Day %2s / %s: %10s\n", day, section, result)
  }
}
