package com.nibado.projects.advent.y2015

import com.nibado.projects.advent.*

object Day07 : Day {
    private val values = resourceLines(2015, 7)

    private val GATE_LITERAL = "([0-9]+) -> ([a-z]+)".toRegex()
    private val GATE_BOOL = "([a-z]+|[0-9]+) (AND|OR) ([a-z]+) -> ([a-z]+)".toRegex()
    private val GATE_SHIFT = "([a-z]+) (LSHIFT|RSHIFT) ([0-9]+) -> ([a-z]+)".toRegex()
    private val GATE_NOT = "NOT ([a-z]+) -> ([a-z]+)".toRegex()

    override fun part1() = values.map { toGate(it) }.toMap().let {
        it.getValue("a").solve(it)
    }
    override fun part2() = TODO()

    fun toGate(line: String): Pair<String, Gate> {
        fun extract(regex: Regex) = regex.matchEntire(line)!!.groupValues.drop(1)
        return when {
            line.matches(GATE_LITERAL) -> extract(GATE_LITERAL).let { (lit, gate) -> gate to LitGate(lit.toInt()) }
            line.matches(GATE_BOOL) -> extract(GATE_BOOL).let { (a, b, mode, gate) -> gate to BoolGate(a, b, mode) }
            line.matches(GATE_SHIFT) -> extract(GATE_SHIFT).let { (inp, mode, am, gate) -> gate to ShiftGate(inp, am.toInt(), mode) }
            line.matches(GATE_NOT) -> extract(GATE_NOT).let { (inp, gate) -> gate to NotGate(inp) }
            else -> throw IllegalArgumentException(line)
        }
    }

    private fun extract(line: String, regex: Regex) = regex.matchEntire(line)!!.groupValues.drop(1)

    interface Gate {
        fun solve(gates: Map<String, Gate>): Int
    }

    data class LitGate(private val value: Int) : Gate {
        override fun solve(gates: Map<String, Gate>) = value
    }

    data class BoolGate(private val gateA: String, private val gateB: String, private val mode: String) : Gate {
        private var value: Int? = null
        override fun solve(gates: Map<String, Gate>) : Int {
            if(value == null) {
                value = (gates.getValue(gateA).solve(gates) to gates.getValue(gateB).solve(gates)).let { (a,b) ->
                    if(mode == "AND") a and b else a or b
                }
            }
            return value!!
        }
    }

    data class ShiftGate(private val gate: String, private val amount: Int, private val mode: String) : Gate {
        private var value: Int? = null
        override fun solve(gates: Map<String, Gate>) : Int {
            if(value == null) {
                val solution = gates.getValue(gate).solve(gates)
                value = if(mode == "LSHIFT") (solution shl amount) and 0xFFFF else solution shr amount
            }
            return value!!
        }
    }

    data class NotGate(private val gate: String) : Gate {
        private var value: Int? = null
        override fun solve(gates: Map<String, Gate>) : Int {
            if(value == null) {
                val solution = gates.getValue(gate).solve(gates)
                value = solution.inv() and 0xFFFF
            }
            return value!!
        }
    }
}

fun main() {
    println(Day07.part1())
}
