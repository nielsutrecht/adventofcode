package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.*

object Day02 : Day {
    private val values = resourceLines(2021, 2).map {
        it.split(' ').let { (dir, amount) ->
            when (dir) {
                "up" -> Direction.NORTH
                "down" -> Direction.SOUTH
                "forward" -> Direction.EAST
                else -> Direction.WEST
            } to amount.toInt()
        }
    }

    override fun part1() = values.fold(Point(0, 0)) { acc, (dir, amount) -> acc.plus(dir, amount) }
            .let { (x, y) -> x.toLong() * y.toLong() }

    override fun part2() = values.fold(Point(0, 0) to 0) { (pos, aim), (dir, amount) ->
        when (dir) {
            Direction.SOUTH -> pos to aim + amount
            Direction.NORTH -> pos to aim - amount
            Direction.EAST -> pos + Point(amount, amount * aim) to aim
            else -> pos to aim
        }
    }.let { (pos, _) -> pos.x.toLong() * pos.y.toLong() }
}