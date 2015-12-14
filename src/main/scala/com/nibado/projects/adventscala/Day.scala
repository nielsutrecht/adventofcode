package com.nibado.projects.adventscala

import java.io.InputStream

import spray.json.{JsObject, JsonParser}

trait Day extends Runnable {
  private def getStream(file: String): InputStream = {
    val stream = getClass.getResourceAsStream(file)
    if(stream == null) {
      throw new IllegalArgumentException("Resource " + file + " not found.");
    }

    stream
  }

  def getResourceAsString(file: String): String = {
    scala.io.Source.fromInputStream(getStream(file)).mkString
  }

  def getResource(file: String): List[String] = {
    scala.io.Source.fromInputStream(getClass.getResourceAsStream(file)).getLines.toList
  }

  def getResourceAsJson(file: String): JsObject = {
    JsonParser(getResourceAsString(file)).asJsObject
  }

  def printAnswer(day: Int, section: String, result: Any): Unit = {
    printf("Day %2s / %s: %10s\n", day, section, result)
  }
}
