package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Point
import com.nibado.projects.advent.resourceLines

object Day03 : Day {
    private val claimRegex = "#([0-9]+) @ ([0-9]+),([0-9]+): ([0-9]+)x([0-9]+)".toRegex()

    private val claims = resourceLines(2018, 3).map(::parse)
    private val pointMap: Map<Point, Int> by lazy {
        val map = mutableMapOf<Point, Int>()
        claims.forEach { it.writeTo(map) }

        map
    }

    override fun part1() = pointMap.count { it.value > 1 }
    override fun part2() = claims.first { r -> r.points().all { p -> pointMap[p]!! == 1 } }.id

    private fun parse(line: String): Claim {
        val (id, x, y, width, height) = claimRegex.matchEntire(line)?.groupValues?.drop(1)?.map { it.toInt() }
                ?: throw IllegalArgumentException("$line does not match")

        return Claim(id, Point(x, y), width, height)
    }

    private data class Claim(val id: Int, val offset: Point, val width: Int, val height: Int) {
        fun points() = (0 until width).flatMap { x -> (0 until height).map { y -> Point(x, y).add(offset) } }
        fun writeTo(map: MutableMap<Point, Int>) =
            points().forEach { map.compute(it) { _, value -> value?.plus(1) ?: 1} }
    }
}