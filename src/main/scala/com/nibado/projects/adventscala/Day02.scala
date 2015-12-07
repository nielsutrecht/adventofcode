package com.nibado.projects.adventscala

class Day02 extends Day {
  override def run(): Unit = {
    val input = getResource("/day02.txt").map(s => toDimension(s))

    printAnswer(2, "One", input.map(d => d.surfaceArea()).sum)
    printAnswer(2, "Two", input.map(d => d.ribbonLength()).sum)
  }

  def toDimension(line: String) : Dimension = {
    val parts = line.split("x")

    new Dimension(parts(0).toInt, parts(1).toInt, parts(2).toInt)
  }

  class Dimension(val h: Int, val l: Int, val w: Int) {

    def surfaceArea(): Int = {
      val side1: Int = l * w
      val side2: Int = w * h
      val side3: Int = h * l
      val min: Int = Math.min(side1, Math.min(side2, side3))

      2 * side1 + 2 * side2 + 2 * side3 + min
    }

    def ribbonLength(): Int = {
      val side1: Int = l + l + w + w
      val side2: Int = w + w + h + h
      val side3: Int = h + h + l + l
      val volume: Int = h * l * w

      Math.min(side1, Math.min(side2, side3)) + volume
    }

    override def toString = {
      h + "x" + l + "x" + w
    }
  }
}
