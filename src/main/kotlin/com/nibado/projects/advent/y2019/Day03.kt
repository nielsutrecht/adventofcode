package com.nibado.projects.advent.y2019

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Direction
import com.nibado.projects.advent.Point
import com.nibado.projects.advent.resourceLines

object Day03 : Day {
    private val wires = resourceLines(2019, 3).map { wire -> wire.split(",").map { Direction.from(it[0]) to it.substring(1).toInt() } }
    private val pointLists = wires.map { toPointList(it) }
    private val intersections =  pointLists.map { list -> list.map { it.first } }.let { (first, second) -> first.intersect(second) }

    private fun toPointList(list: List<Pair<Direction, Int>>): List<Pair<Point, Int>> {
        var current = Point(0, 0)
        val points = mutableListOf<Pair<Point, Int>>()
        var distance = 0

        list.forEach { (dir, steps) ->
            repeat(steps) {
                current += dir
                distance++
                points += current to distance
            }
        }

        return points
    }

    override fun part1() = intersections.map { it.manhattan() }.min()!!
    override fun part2() : Any {
        val (first, second) = pointLists.map { list -> list.filter { intersections.contains(it.first) }.toMap() }
        return first.map { it.value + second.getValue(it.key) }.min()!!
    }
}