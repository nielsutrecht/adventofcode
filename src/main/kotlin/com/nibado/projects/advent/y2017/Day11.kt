package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceString
import com.nibado.projects.advent.y2017.Day11.HexDir.*

object Day11 : Day {
    private val hexMap = mapOf(Pair("n", N),Pair("ne", NE),Pair("se", SE),Pair("s", S),Pair("sw", SW),Pair("nw", NW))
    private val input = resourceString(11).split(",").map {hexMap[it]!! }

    override fun part1() = distance(input.map { it.p }.fold(Point(0, 0), {a, b -> a.add(b)})).toString()
    override fun part2() = input.map { Pair(0, it.p) }.fold(Pair(0, Point(0, 0)), {a, b -> Pair(Math.max(a.first, maxDistance(a.second, b.second)), a.second.add(b.second))}).first.toString()

    private fun maxDistance(a: Point, b: Point) = Math.max(distance(a), distance(b))
    private fun distance(p: Point) = Math.max(Math.abs(p.x), Math.abs(p.y))

    enum class HexDir constructor(var p: Point) {
        N(Point(0, -1)),
        NE(Point(1, -1)),
        SE(Point(1, 0)),
        S(Point(0, 1)),
        SW(Point(-1, 1)),
        NW(Point(-1, 0))
    }
}