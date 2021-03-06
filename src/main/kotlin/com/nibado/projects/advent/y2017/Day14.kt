package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.*
import com.nibado.projects.advent.Point

object Day14 : Day {
    private val rows = (0..127).map { "amgozmfv-$it" }
    private val square : List<String> by lazy { rows.map { hash(it) }.map { toBinary(it) } }

    override fun part1() = square.sumBy { it.count { it == '1' } }.toString()

    override fun part2(): String {
        val points = (0 .. 127).flatMap { x -> (0 .. 127 ).map { y -> Point(x, y) } }.toMutableSet()
        val visited = points.filter { square[it.y][it.x] == '0'}.toMutableSet()

        points.removeAll(visited)

        var count = 0
        while(!points.isEmpty()) {
            count++
            fill(visited, points, points.first())
        }

        return count.toString()
    }

    private fun fill(visited: MutableSet<Point>, points: MutableSet<Point>, current: Point) {
        visited += current
        points -= current

        current.neighborsHv().filter { points.contains(it) }.forEach { fill(visited, points, it) }
    }

    private fun hash(input: String): String {
        val chars = input.toCharArray().map { it.toInt() } + listOf(17, 31, 73, 47, 23)

        return (0..15).map { Day10.knot(chars, 64).subList(it * 16, it * 16 + 16) }.map { xor(it) }.toHex()
    }
}