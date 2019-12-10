package com.nibado.projects.advent.y2019

import com.nibado.projects.advent.*

object Day10 : Day {
    val test = """
......#.#.
#..#.#....
..#######.
.#.#.###..
.#..#.....
..#....#.#
#..#....#.
.##.#..###
##...#..#.
.#....####
    """.trimIndent()

    private val map = resourceLines(2019, 10).map { line -> line.toCharArray().toList() }
    private val map2 = test.split("\n").map { line -> line.toCharArray().toList() }

    override fun part1() = best(toPoints(map2))
    override fun part2() = TODO()

    fun best(points: Collection<Point>): Pair<Point, Int> {
        return points.map { it to visible(it, points) }.maxBy { it.second }!!
    }

    fun toPoints(map: List<List<Char>>): Set<Point> {
        return map.indices.flatMap { y -> map[y].indices.map { x -> x to y } }
            .filter { (x, y) -> map[y][x] == '#' }
            .map { it.toPoint() }
            .toSet()
    }

    fun visible(p: Point, points: Collection<Point>): Int {
        var counter = 0
        var visited = 0
        for (other in points.filterNot { it == p }) {
            val line = Line(p, other)
            val distanceToOther = p.distance(other)
            val angleToOther = p.angle(other)

            val onLine = points.filterNot { it == p || it == other }
                .filter { line.distanceTo(it) <= 0.001 }

            val closer = onLine.filter { p.distance(it) < distanceToOther }
            val angled = closer.filter { p.angle(it) == angleToOther }
            val blocked = angled.isNotEmpty()

            val distances = points.filterNot { it == p || it == other }.filterNot { p.distance(it) > distanceToOther }
                .map { third -> third to line.distanceTo(third) }


            if (!blocked) {
                counter++
            }
            visited++
        }

        return counter
    }


    fun test() {
        //val points = listOf(Point(0, 0), Point(3, 1), Point(6, 2), Point(2,1))

        val points = toPoints(map2)
        println(visible(points.first(), points))
    }


    fun print(map: List<List<Char>>) {
        println(map.map { it.joinToString("") }.joinToString("\n"))
    }


}

fun main() {
    Day10.test()
}