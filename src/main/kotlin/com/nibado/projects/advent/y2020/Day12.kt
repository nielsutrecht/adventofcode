package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*
import com.nibado.projects.advent.Direction.*
import java.lang.IllegalArgumentException

object Day12 : Day {
    val values = resourceRegex(2020, 12, "([A-Z])([0-9]+)").map { (_,a,b) -> a to b.toInt() }

    override fun part1() : Int = values.fold(Point(0,0) to EAST) { (current, dir), (inst, amount) ->
        when(inst) {
            "N" -> current.plus(NORTH, amount) to dir
            "E" -> current.plus(EAST, amount) to dir
            "S" -> current.plus(SOUTH, amount) to dir
            "W" -> current.plus(WEST, amount) to dir
            "L" -> current to dir.left(amount)
            "R" -> current to dir.right(amount)
            "F" -> current.plus(dir, amount) to dir
            else -> throw IllegalArgumentException(inst)
        }
    }.first.manhattan()

    override fun part2() : Int = values.fold(Point(0,0) to Point(10,-1)) { (ship, wp), (inst, amount) ->
        when(inst) {
            "N" -> ship to wp.plus(NORTH, amount)
            "E" -> ship to wp.plus(EAST, amount)
            "S" -> ship to wp.plus(SOUTH, amount)
            "W" -> ship to wp.plus(WEST, amount)
            "L" -> ship to wp.rotate(-amount)
            "R" -> ship to wp.rotate(amount)
            "F" -> ship + wp * amount to wp
            else -> throw IllegalArgumentException(inst)
        }
    }.first.manhattan()

    private fun Direction.left(degrees: Int) = (1 .. degrees / 90).fold(this) {acc, _-> acc.ccw() }
    private fun Direction.right(degrees: Int) = (1 .. degrees / 90).fold(this) {acc, _-> acc.cw() }
}
