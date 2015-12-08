package com.nibado.projects.adventscala

import scala.collection.mutable

class Day07 extends Day {
  val gatePattern = "^((?<w1>[0-9a-z]{1,3}) )?((?<gate>AND|OR|NOT|RSHIFT|LSHIFT) )?(?<w2>[0-9a-z]{1,5}) -> (?<wO>[a-z]{1,2})$".r.pattern

  override def run(): Unit = {
    val input = getResource("/day07.txt")

    val network1 = createNetwork(input)
    val network2 = createNetwork(input)

    val result1 = network1.get("a").get.solve()

    printAnswer(7, "One", result1)
    network2.get("b").get.value = result1
    printAnswer(7, "Two", network2.get("a").get.solve())
  }

  def createNetwork(input: List[String]): mutable.HashMap[String, Wire] = {
    val map = new mutable.HashMap[String, Wire]()

    def getWire(id: String): Option[Wire] = {
      if(id == null) {
        return null
      }
      if(id.matches("[0-9]{1,5}")) {
        val wire = new Wire()
        wire.value = id.toInt

        return Option(wire)
      }

      if(!map.contains(id)) {
        map.put(id, new Wire())
      }

      map.get(id)
    }

    input.map(s => gatePattern.matcher(s)).filter(m => m.matches()).foreach(m => {
      val wireOut: Wire = getWire(m.group("wO")).get

      wireOut.gate = new Gate(m.group("gate"), getWire(m.group("w1")), getWire(m.group("w2")))
    })

    map
  }

   class Wire {
    var value: Int = -1
    var gate: Gate = null

    def solve() : Int = {
      if(value < 0) {
        value = gate.solve()
      }
      value
    }
  }

  class Gate(val gate: String, val wire1: Option[Wire], val wire2: Option[Wire]) {
    def solve(): Int =
      gate match {
        case null => wire2.get.solve()
        case "AND" => wire1.get.solve() & wire2.get.solve()
        case "OR" => wire1.get.solve() | wire2.get.solve()
        case "NOT" => (~wire2.get.solve()) & 0xFFFF
        case "LSHIFT" => wire1.get.solve() << wire2.get.solve()
        case "RSHIFT" => wire1.get.solve() >> wire2.get.solve()
        case _ => throw new RuntimeException("Unknown gate: " + gate)
      }
  }
}
