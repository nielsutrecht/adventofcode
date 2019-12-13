package com.nibado.projects.advent.y2019

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Point

object Day13 : Day {
    private val program = resourceIntCode(13)

    private fun IntCode.takePaintInstruction() =
        if (!terminated) Point(output.take().toInt(), output.take().toInt()) to output.take().toInt() else null

    override fun part1(): Int {
        val computer = IntCode(program)
        val out = computer.output
        var count = 0
        while (!computer.terminated) {
            while (!computer.terminated && out.size < 3) computer.step()

            computer.takePaintInstruction()?.run { if (second == 2) count++ }
        }

        return count
    }

    override fun part2(): Int {
        val computer = IntCode(program).also { it.memory.set(0, 2) }

        var ball: Point
        var paddle = Point(0, 0)
        var score = 0
        while (!computer.terminated) {
            while (!computer.terminated && computer.output.size < 3) computer.step()

            computer.takePaintInstruction()?.run {
                when {
                    second == 3 -> paddle = first
                    second == 4 -> {
                        ball = first

                        computer.input += when {
                            ball.x > paddle.x -> 1L
                            ball.x < paddle.x -> -1L
                            else -> 0L
                        }
                    }
                    first == Point(-1, 0) -> score = second
                }
            }
        }

        return score
    }
}