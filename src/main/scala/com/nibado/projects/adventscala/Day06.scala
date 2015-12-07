package com.nibado.projects.adventscala

import java.awt.Point
import java.util.regex.Pattern


class Day06 extends Day {
  val width = 1000
  val pattern = Pattern.compile("(toggle|turn on|turn off) ([0-9]{1,3}),([0-9]{1,3}) through ([0-9]{1,3}),([0-9]{1,3})")

  val boolOperations = Map[String, (Boolean => Boolean)]("turn on" -> (b => true), "turn off" -> (b => false), "toggle" -> (b => !b))
  val intOperations = Map[String, (Int => Int)]("turn on" -> (i => i + 1), "turn off" -> (i => Math.max(0, i - 1)), "toggle" -> (i => i + 2))

  override def run(): Unit = {
    val actions = getResource("/day06.txt").map(s => toAction(s))
    val boolArray = new Array[Boolean](width * width)
    val intArray = new Array[Int](width * width)

    actions.foreach(a => a.apply(boolArray))
    printAnswer(6, "One", boolArray.count(b => b))

    actions.foreach(a => a.apply(intArray))
    printAnswer(6, "Two", intArray.sum)
  }

  def toAction(line: String): Action = {
    val m = pattern.matcher(line)
    if (!m.matches()) {
      throw new RuntimeException("Illegal input: " + line)
    }

    new Action(m.group(1),
      new Point(m.group(2).toInt, m.group(3).toInt),
      new Point(m.group(4).toInt, m.group(5).toInt))
  }

  class Action(val operation: String, val from: Point, val to: Point) {
    def apply(array: Array[Boolean]) = {
      val fun = boolOperations(operation)

      for(y <- from.y to to.y) {
        for(x <- from.x to to.x) {
          val i = index(x, y)
          array(i) = fun(array(i))
        }
      }
    }

    def apply(array: Array[Int]) = {
      val fun = intOperations(operation)

      for(y <- from.y to to.y) {
        for(x <- from.x to to.x) {
          val i = index(x, y)
          array(i) = fun(array(i))
        }
      }
    }

    def index(x: Int, y: Int): Int = {
      x + y * width
    }
  }
}
