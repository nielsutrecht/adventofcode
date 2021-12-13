package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.*

object Day13 : Day {
    private val input = resourceStrings(2021, 13).let { (first, second) ->
        first.split("\n").map(Point::parse) to second.split("\n")
    }

    private val instructions = input.second.map {
        it.matchGroups("fold along ([x|y])=([0-9]+)".toRegex()).let { (_, a, b) -> a to b.toInt() }
    }

    private fun solve(onlyFirst: Boolean): Set<Point> {
        val grid =  input.first.toMutableSet()

        instructions.take(if(onlyFirst) 1 else instructions.size).forEach { (xy, value) ->
            val reflect = if(xy == "x") grid.filter { it.x > value }
                .map { it to Point(value - (it.x - value), it.y) }
            else grid.filter { it.y > value }
                .map { it to Point(it.x, value - (it.y - value)) }

            grid -= reflect.map { it.first }.toSet()
            grid += reflect.map { it.second }
        }

        return grid
    }

    private fun gridToString(grid: Set<Point>) : String {
        val width = grid.maxX()!! + 1
        val points = (0 until grid.maxY()!! + 1).flatMap { y -> (0 until width).map { x -> Point(x, y) } }
        return points.map { if(grid.contains(it)) '#' else '.' }
            .chunked(width).joinToString("\n") { it.joinToString("") }
    }

    override fun part1(): Int = solve(true).size
    override fun part2() = gridToString(solve(false))
}