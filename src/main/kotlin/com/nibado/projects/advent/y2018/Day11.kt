package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Point
import kotlin.math.min

object Day11 : Day {
    private val grid = Grid(6392)

    class Grid(serial: Int) {
        private val grid = IntArray(300 * 300)

        init {
            for(x in 1 .. 300) {
                for(y in 1 .. 300) {
                    val rackId = x + 10
                    grid[toIndex(x, y)] = hundred((rackId * y + serial) * rackId) - 5
                }
            }
        }

        fun get(x: Int, y: Int, size: Int = 3) : Int {
            var sum = 0

            for(dx in 0 until size) {
                for(dy in 0 until size) {
                    sum += grid[toIndex(x + dx, y + dy)]
                }
            }

            return sum
        }

        private fun hundred(v: Int) = ((v % 1000) - (v % 100)) / 100
        private fun toIndex(x: Int, y: Int) = x - 1 + (y - 1) * 300
    }

    private fun maxSize(p: Point) = min(300 - p.x, 300 - p.y)

    override fun part1(): String {
        return (1..298).flatMap { y -> (1..298).map { x -> Point(x, y) } }
                .maxBy { grid.get(it.x, it.y) }
                ?.let { "${it.x},${it.y}" }!!
    }

    override fun part2(): String {
        return (1..300).flatMap { y -> (1..300).map { x -> Point(x, y) } }
                .flatMap { p -> (1..maxSize(p)).map { p to it } }
                .maxBy { grid.get(it.first.x, it.first.y, it.second) }
                ?.let { "${it.first.x},${it.first.y},${it.second}" }!!
    }
}

fun main(args: Array<String>) {
    println(Day11.part1())
    //println(Day11.part2())
}