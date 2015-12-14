package com.nibado.projects.adventscala

class Day11 extends Day {
  val illegal = "ilo".toList

  override def run(): Unit = {
    val password = new StringBuilder(getResourceAsString("/day11.txt"))

    printAnswer(11, "One", nextValid(password))
    printAnswer(11, "Two", nextValid(password))
  }

  def increment(password: StringBuilder): Unit = {
    (password.length - 1 to 0 by -1).foreach(i => {
      password.update(i, (password(i) + 1).toChar)
      if(password(i) > 'z') {
        password.update(i, 'a')
      }
      else {
        return
      }
    })
  }

  def nextValid(password: StringBuilder): StringBuilder = {
    do {
      increment(password)
    }
    while(!threeStraight(password) || containsIllegal(password) || !hasTwoPairs(password))

    password
  }

  def threeStraight(s: StringBuilder) =
    s.toList.sliding(3).exists { case Seq(a, b, c) =>
      a - b == -1 && b - c == -1
    }

  def containsIllegal(s: StringBuilder) =
    s.exists(c => illegal.contains(c))

  def hasTwoPairs(s: StringBuilder) = {
    val pairs = s.sliding(2).toSeq

    pairs.lastIndexWhere(l => l(0) == l(1)) - pairs.indexWhere(l => l(0) == l(1)) > 1
  }
}
