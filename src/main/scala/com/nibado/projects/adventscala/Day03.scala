package com.nibado.projects.adventscala

import scala.collection.mutable

class Day03 extends Day {
  override def run(): Unit = {
    var visited = new mutable.HashSet[Point]
    val input = getResourceAsString("/day03.txt")

    var current = new Point(0, 0)
    visited += current
    input.foreach(c => {
      current = current.next(c)
      visited += current
    })

    printAnswer(3, "One", visited.size)

    visited = new mutable.HashSet[Point]
    var santa = new Point(0, 0)
    var robot = new Point(0, 0)
    visited += santa

    input.sliding(2,2).foreach(t => {
      santa = santa.next(t(0))
      robot = robot.next(t(1))

      visited += santa
      visited += robot
    })

    printAnswer(3, "Two", visited.size)
  }

  class Point(val x: Int, val y: Int) {
    def next(c: Char): Point = {
      c match {
        case '>' => new Point(x + 1, y)
        case '<' => new Point(x - 1, y)
        case '^' => new Point(x, y - 1)
        case 'v' => new Point(x, y + 1)
        case _ => throw new RuntimeException("Illegal argument: " + c)
      }
    }

    def canEqual(other: Any): Boolean = other.isInstanceOf[Point]

    override def equals(other: Any): Boolean = other match {
      case that: Point =>
        (that canEqual this) &&
          x == that.x &&
          y == that.y
      case _ => false
    }

    override def hashCode(): Int = {
      31 * x + y
    }
  }
}
