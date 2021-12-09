package com.nibado.projects.advent.viz

import com.nibado.projects.advent.*
import com.nibado.projects.advent.graphics.*
import java.awt.Color
import java.io.File

object Year2021Day09 {
    private val values = resourceLines(2021, 9).map { it.toCharArray().map { it.digitToInt() } }
    private val points = values.indices.flatMap { y -> values[0].indices.map { x -> Point(x, y) } }
    val gif = AnimatedGif(0, 10)
    fun part1() = points.map { it to values[it.y][it.x] }.filter {
        val neighbors = it.first.neighborsHv().filter { it.inBound(values[0].size - 1, values.size - 1) }
        neighbors.all { n -> values[n.y][n.x] > it.second }
    }.sumOf { it.second + 1 }

    fun part2() : Int {
        val consider = points.filterNot { (x, y) -> values[y][x] == 9 }.toMutableSet()
        val sizes = mutableListOf<Int>()

        while(consider.isNotEmpty()) {
            var area = 0
            var frontier = setOf(consider.first())

            while(frontier.isNotEmpty()) {
                area += frontier.size
                consider -= frontier
                writeStep(consider)
                frontier = frontier.flatMap { it.neighborsHv() }.filter { consider.contains(it) }.toSet()
            }
            sizes += area
            println(consider.size)
        }

        return sizes.sorted().takeLast(3).let { (a,b,c) -> a * b * c }
    }

    fun writeStep(consider: Set<Point>) {
        val img = Images.create(values[0].size * CELL_WIDTH, values.size * CELL_WIDTH)

        points.forEach { p ->
            val (x, y) = p
            val g = img.graphics
            val value = values[y][x]
            val color = if(value == 9 || p in consider) {
                COLOR_MAP[value]
            } else {
                Color.BLUE
            }
            g.color = color
            g.fillRect(x * CELL_WIDTH, y * CELL_WIDTH, CELL_WIDTH, CELL_WIDTH)
        }

        gif += img
    }

    private val COLOR_MAP = (0..9).map { it to 100 + it * 15 }.associate { (i, c) -> i to Color(c, c, c) }
    private const val CELL_WIDTH = 4
}

fun main() {
    Year2021Day09.part2()
    Year2021Day09.gif.encodeTo(File("target/ani.gif"))
}