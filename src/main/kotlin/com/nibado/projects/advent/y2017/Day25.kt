package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Direction
import com.nibado.projects.advent.collect.LList
import com.nibado.projects.advent.resourceLines

object Day25 : Day {
    private val input = resourceLines(25).map { it.trim() }.filterNot { it.isEmpty() }

    private fun parse(input: List<String>) : Turing {
        val begin = Regex("Begin in state ([A-Z]).")
        val iters = Regex("Perform a diagnostic checksum after ([0-9]+) steps.")
        val inState = Regex("In state ([A-Z]):")
        val write = Regex("- Write the value ([0-1]).")
        val move = Regex("- Move one slot to the (right|left).")
        val cont = Regex("- Continue with state ([A-Z]).")

        var i = 0

        val state = begin.matchEntire(input[i++])!!.groupValues[1]
        val iterations = iters.matchEntire(input[i++])!!.groupValues[1].toInt()
        val stateMap = mutableMapOf<String, State>()

        while(i < input.size) {
            val stateVal = inState.matchEntire(input[i++])!!.groupValues[1]
            i++
            val ins0 = Instructions(
                    write.matchEntire(input[i++])!!.groupValues[1].toInt(),
                    if(move.matchEntire(input[i++])!!.groupValues[1] == "right") Direction.EAST else Direction.WEST,
                    cont.matchEntire(input[i++])!!.groupValues[1]
            )
            i++
            val ins1 = Instructions(
                    write.matchEntire(input[i++])!!.groupValues[1].toInt(),
                    if(move.matchEntire(input[i++])!!.groupValues[1] == "right") Direction.EAST else Direction.WEST,
                    cont.matchEntire(input[i++])!!.groupValues[1]
            )

            stateMap[stateVal] = State(ins0, ins1)
        }

        return Turing(state, iterations, stateMap)
    }

    override fun part1() : String {
        val program = parse(input)
        program.run()

        return program.values().count { it == 1 }.toString()

    }

    override fun part2() = "Done!"

    class Turing(private var state: String, private var iterations: Int, private var stateMap: Map<String, State>) {
        private var tape = LList(null, null, 0)

        fun values() = tape.first().values()

        fun run() = repeat(iterations, {tick()})

        private fun tick() {
            val state = stateMap[state]!!
            val ins = if(tape.value() == 0) state.ins0 else state.ins1

            tape.value(ins.write)
            this.state = ins.next

            when(ins.move) {
                Direction.EAST -> {
                    if(!tape.hasNext()) {
                        tape.addNext(0)
                    }
                    tape = tape.next()
                }
                Direction.WEST -> {
                    if(!tape.hasPrev()) {
                        tape.addPrev(0)
                    }
                    tape = tape.prev()
                }
            }
        }
    }

    data class State(val ins0: Instructions, val ins1: Instructions)
    data class Instructions(val write: Int, val move: Direction, val next: String)
}



