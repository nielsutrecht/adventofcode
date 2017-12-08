package com.nibado.projects.advent.y2016

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Direction
import com.nibado.projects.advent.Point
import com.nibado.projects.advent.resourceLines

object Day02 : Day {
    private val input = resourceLines(2016, 2).map { it.toCharArray().toList() }.toList()

    private val keypad1 = KeyPad(arrayOf(
            arrayOf('1', '2', '3'),
            arrayOf('4', '5', '6'),
            arrayOf('7', '8', '9')
    ))

    private val keypad2 = KeyPad(arrayOf(
            arrayOf(' ', ' ', '1', ' ', ' '),
            arrayOf(' ', '2', '3', '4', ' '),
            arrayOf('5', '6', '7', '8', '9'),
            arrayOf(' ', 'A', 'B', 'C', ' '),
            arrayOf(' ', ' ', 'D', ' ', ' ')
    ))

    override fun part1() = solve(keypad1)
    override fun part2() = solve(keypad2)

    private fun solve(keypad: KeyPad) : String {
        var solution = ""

        input.forEach {
            solution += keypad.apply(it)
        }

        return solution
    }

    private class KeyPad(val chars: Array<Array<Char>>) {
        private val width = chars.size
        private var pos = Point(width / 2, width / 2)

        fun apply(presses: List<Char>) : Char {
            presses.forEach {
                val dir = when(it) {
                    'U' -> Direction.NORTH
                    'L' -> Direction.WEST
                    'D' -> Direction.SOUTH
                    'R' -> Direction.EAST
                    else -> { throw IllegalArgumentException(it.toString())}
                }

                val newPos = pos.add(dir)
                if(newPos.inBound(width - 1, width - 1) && chars[newPos.y][newPos.x] != ' ') {
                    pos = newPos
                }
            }

            return chars[pos.y][pos.x]
        }
    }
}