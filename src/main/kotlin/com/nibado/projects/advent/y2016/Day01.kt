package com.nibado.projects.advent.y2016

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Direction
import com.nibado.projects.advent.Point
import com.nibado.projects.advent.resourceString

object Day01 : Day {
    private val instructions = resourceString(2016, 1).split(", ").map { Pair(it[0], it.substring(1).trim().toInt()) }.toList()
    private val solution : Pair<Point, Point> by lazy { solve() }

    private fun solve(): Pair<Point, Point> {
        var pos = Point(0, 0)
        var dir = Direction.NORTH
        val positions: MutableSet<Point> = mutableSetOf()

        var firstDouble: Point? = null

        instructions.forEach {
            dir = if(it.first == 'L') dir.ccw() else dir.cw()

            (0 until it.second).forEach {
                pos = pos.add(dir)
                if(firstDouble == null && positions.contains(pos)) {
                    firstDouble = pos
                }
                positions += pos
            }
        }
        return Pair(pos, firstDouble!!)
    }

    override fun part1() = solution.first.manhattan().toString()
    override fun part2() = solution.second.manhattan().toString()
}