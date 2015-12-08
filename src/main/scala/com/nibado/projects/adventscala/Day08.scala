package com.nibado.projects.adventscala

class Day08 extends Day {
  override def run(): Unit = {
    val input = getResource("/day08.txt")

    printAnswer(8, "One", input.map(s => s.length - unescape(s).length).sum)
    printAnswer(8, "Two", input.map(s => escape(s).length - s.length).sum)
  }

  def unescape(input: String): String = {
    val it = input.substring(1, input.length - 1).iterator
    val out = new StringBuilder
    while(it.hasNext) {
      val c = it.next()

      if(c == '\\') {
        val c2 = it.next()
        if(c2 == '"' || c2 == '\\') {
          out.append(c2)
        }
        else if(c2 == 'x') {
          val v = Integer.parseInt(it.next().toString + it.next.toString, 16)
          out.append(v.toChar)
        }
      }
      else {
        out.append(c)
      }
    }

    out.toString()
  }

  def escape(input: String): String = {
    '"' + input.flatMap(c => {
      if(c == '"' || c == '\\') List('\\', c) else List(c)
    }) + '"'
  }
}