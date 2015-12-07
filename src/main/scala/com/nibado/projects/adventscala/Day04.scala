package com.nibado.projects.adventscala

import java.security.MessageDigest

class Day04 extends Day {
  val md5 = MessageDigest.getInstance("md5")
  val input = "iwrupvqb".getBytes

  override def run(): Unit = {
    printAnswer(4, "One", find(5))
    printAnswer(4, "Two", find(6))
  }

  def digest(i: Int): Array[Byte] = {
    md5.update(input)
    md5.digest(i.toString.getBytes)
  }

  def find(zeroes: Int) : Int = {
    def allZero(arr: Array[Byte], index: Int) : Boolean = {
      val mask = if(index % 2 == 0) 0xF0 else 0x0F

      val res = (arr(index / 2) & mask) == 0

      if(index == 0) res else res && allZero(arr, index - 1)
    }

    for(i <- 0 until Int.MaxValue) {
      val array = digest(i)
      if(allZero(array, zeroes - 1)) {
        return i
      }
    }

    -1
  }
}