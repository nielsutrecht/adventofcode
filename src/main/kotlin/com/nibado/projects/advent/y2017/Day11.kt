package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceString

object Day11 : Day {
    private val input = resourceString(11).split(",").map {HexDir.valueOf(it.toUpperCase()) }
    private val solution: Pair<Int, Point> by lazy { input.map { Pair(0, it.p) }
                .fold(Pair(0, Point(0, 0)), {a, b -> Pair(maxOf(a.first, distance(a.second)), a.second.add(b.second))}) }

    override fun part1() = distance(solution.second).toString()
    override fun part2() = solution.first.toString()

    private fun distance(p: Point) = maxOf(Math.abs(p.x), Math.abs(p.y))

    enum class HexDir constructor(var p: Point) { N(Point(0, -1)), NE(Point(1, -1)), SE(Point(1, 0)), S(Point(0, 1)), SW(Point(-1, 1)), NW(Point(-1, 0)) }
}