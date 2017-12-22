package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Direction
import com.nibado.projects.advent.Point
import com.nibado.projects.advent.resourceLines

object Day22 : Day {
    private val input = resourceLines(22).mapIndexed{ y, s -> s.trim().toCharArray().mapIndexed { x, c -> Point(x, y) to (if(c == '#') State.INFECTED else State.CLEAN)} }.flatMap { it }.toMap()
    private enum class State { CLEAN, WEAKENED, INFECTED, FLAGGED }

    override fun part1() : String {
        val points = input.toMutableMap()

        val min = min(points.keys)
        val max = max(points.keys)

        var current = Point(min.x + (max.x - min.x) / 2, min.y + (max.y - min.y) / 2)
        var direction = Direction.NORTH
        var count = 0

        (1 .. 10000).forEach {
            val infected = points.getOrDefault(current, State.CLEAN) == State.INFECTED
            direction = if(infected) direction.cw() else direction.ccw()
            points[current] = if(infected) State.CLEAN else State.INFECTED
            current = current.add(direction)

            count += if(!infected) 1 else 0
        }

        return count.toString()
    }

    override fun part2() : String {
        val points = input.toMutableMap()

        val min = min(points.keys)
        val max = max(points.keys)

        var current = Point(min.x + (max.x - min.x) / 2, min.y + (max.y - min.y) / 2)
        var direction = Direction.NORTH
        var count = 0

        (1 .. 10000000).forEach {
            val state = points.getOrDefault(current, State.CLEAN)
            direction = when(state) {
                State.CLEAN -> direction.ccw()
                State.INFECTED -> direction.cw()
                State.FLAGGED -> direction.cw().cw()
                State.WEAKENED -> direction
            }
            points[current] = when(state) {
                State.CLEAN -> State.WEAKENED
                State.WEAKENED -> {
                    count++
                    State.INFECTED
                }
                State.INFECTED -> State.FLAGGED
                State.FLAGGED -> State.CLEAN
            }

            current = current.add(direction)
        }

        return count.toString()
    }

    fun print(points: Map<Point, Boolean>, center: Point) {
        val min = min(points.keys)
        val max = max(points.keys)

        for(y in min.y .. max.y) {
            for(x in min.x .. max.x) {
                if(center == Point(x, y)) {
                    print("*")
                } else {
                    print(if (points[Point(x, y)]!!) '#' else '.')
                }
            }
            println()
        }
    }

    fun min(points: Collection<Point>) = Point(points.minBy { it.x }!!.x, points.minBy { it.y }!!.y)
    fun max(points: Collection<Point>) = Point(points.maxBy { it.x }!!.x, points.maxBy { it.y }!!.y)
}

fun main(args: Array<String>) {
    println(Day22.part2())
}