package com.nibado.projects.adventscala

import spray.json._

class Day12 extends Day {
  override def run(): Unit = {
    val json = getResourceAsJson("/day12.txt")

    printAnswer(12, "One", sumTree(json, n => false))
    printAnswer(12, "Two", sumTree(json, n => hasValue(n, "red")))
  }

  def sumTree(node: JsValue, filter: (Map[String, JsValue] => Boolean)) : Int = {
    node match {
      case JsNumber(n) => n.intValue()
      case JsArray(a) => a.map(e => sumTree(e, filter)).sum
      case JsObject(obj) => if(filter(obj)) { 0 } else obj.values.map(v => sumTree(v, filter)).sum
      case _ => 0
    }
  }

  def hasValue(obj: Map[String, JsValue], value: String) : Boolean = {
    obj.values.exists { case JsString(s) => s.equals(value) case _ => false }
  }
}
