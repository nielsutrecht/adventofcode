package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Direction
import com.nibado.projects.advent.Point
import com.nibado.projects.advent.resourceLines

object Day22 : Day {
    private val input = resourceLines(2017, 22).mapIndexed { y, s -> s.trim().toCharArray().mapIndexed { x, c -> Point(x, y) to (if (c == '#') State.INFECTED else State.CLEAN) } }.flatMap { it }.toMap()

    private enum class State { CLEAN, WEAKENED, INFECTED, FLAGGED }

    override fun part1() = solve(10000,
            { s, d ->
                when (s) {
                    State.INFECTED -> d.cw()
                    else -> d.ccw()
                }
            },
            {
                when (it) {
                    State.CLEAN -> State.INFECTED
                    else -> State.CLEAN
                }
            })

    override fun part2() = solve(10000000,
            { s, d ->
                when (s) {
                    State.CLEAN -> d.ccw()
                    State.INFECTED -> d.cw()
                    State.FLAGGED -> d.cw().cw()
                    State.WEAKENED -> d
                }
            },
            {
                when (it) {
                    State.CLEAN -> State.WEAKENED
                    State.WEAKENED -> State.INFECTED
                    State.INFECTED -> State.FLAGGED
                    State.FLAGGED -> State.CLEAN
                }
            })

    private fun solve(iterations: Int, dir: (State, Direction) -> Direction, state: (State) -> State): String {
        val points = input.toMutableMap()

        val min = Point(points.keys.minBy { it.x }!!.x, points.keys.minBy { it.y }!!.y)
        val max = Point(points.keys.maxBy { it.x }!!.x, points.keys.maxBy { it.y }!!.y)

        var current = Point(min.x + (max.x - min.x) / 2, min.y + (max.y - min.y) / 2)
        var direction = Direction.NORTH
        var count = 0

        (1..iterations).forEach {
            direction = dir(points[current] ?: State.CLEAN, direction)
            points[current] = state(points[current] ?: State.CLEAN)

            if (points[current] == State.INFECTED) {
                count++
            }

            current = current.add(direction)
        }

        return count.toString()
    }
}