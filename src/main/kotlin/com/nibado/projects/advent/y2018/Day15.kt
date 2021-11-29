package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Point
import com.nibado.projects.advent.collect.CharMap
import com.nibado.projects.advent.resourceLines

object Day15 : Day {
    val input = resourceLines(2018, 17)

    fun log(s: String) {
        //println(s)
    }

    data class Entity(val type: Char, var pos: Point, var hitpoints: Int = 200) {
        val power = 3
        fun turn(grid: Grid): Boolean {
            if(hitpoints <= 0) {
                return false
            }

            //Each unit begins its turn by identifying all possible targets (enemy units).
            // If no targets remain, combat ends.

            val targets = grid.entities().filter(::isTarget)

            if (targets.isEmpty()) {
                return true
            }

            val targetSquares = targets.map { it to grid.squaresFor(it) }.filter { it.second.isNotEmpty() }
                    .flatMap { it.second }

            val inRange = targetSquares.filter { it.neighborsHv().contains(it) }.sorted()

            if(inRange.isEmpty()) {
                log(grid.toString(targetSquares.toSet(), '?'))

                val paths = targetSquares.map { it to grid.search(pos, it) }
                        .filterNot { it.second.isEmpty() }

                log(grid.toString(paths.map { it.first }.toSet(), '@'))

                if(paths.isNotEmpty()) {
                    val minDistance = paths.minByOrNull { it.second.size }!!.second.size

                    val closest = paths.filter { it.second.size == minDistance }

                    log(grid.toString(closest.map { it.first }.toSet(), '!'))


                    val chosen = closest.sortedBy { it.first }

                    log(grid.toString(setOf(chosen.first().first), '+'))

                    if(chosen.isNotEmpty()) {
                        val nextStep = chosen.first().second[1]

                        grid.move(this, nextStep)
                    }

                    log(grid.toString())
                }

            }

            attack(grid)

            return false
        }

        private fun attack(grid: Grid) {
            val targets = grid.entities().filter(::isTarget)
            val targetSquares = targets.map { it to grid.squaresFor(it) }.filter { it.second.isNotEmpty() }
                    .flatMap { it.second }

            val inRange = targetSquares.filter { it.neighborsHv().contains(it) }.sorted()
            val select = inRange.map { grid.entities[it]!! }.filter { it.type != type }.sortedBy { it.hitpoints }.sortedBy { it.pos }

            if(select.isNotEmpty()) {
                select.first().hitpoints -= power
            }
        }


        fun isTarget(entity: Entity) = entity.hitpoints > 0 && entity.type != this.type
    }

    class Grid(val grid: CharMap, val entities: MutableMap<Point, Entity>) {
        val points = grid.points()

        val openPoints = points.filter { get(it) == '.' }.toSet()

        override fun toString() = toString(emptySet())

        fun toString(other: Set<Point>, otherChar: Char = ' '): String {
            val build = StringBuilder(grid.width * grid.height)

            for (y in 0 until grid.height) {
                for (x in 0 until grid.width) {
                    val p = Point(x, y)
                    val c = if (other.contains(p)) {
                        otherChar
                    } else {
                        entities[p]?.type ?: get(p)
                    }

                    build.append(c)
                }
                build.append('\n')
            }

            return build.toString()
        }

        fun inBounds(p: Point) = grid.inBounds(p)

        fun squaresFor(e: Entity) = e.pos.neighborsHv().filter { openPoints.contains(it) }.filterNot { entities.keys.contains(it) }
        fun entities() = points.mapNotNull { entities[it] }
        fun get(p: Point) = grid[p]
        fun impassable(p: Point) = entities.keys.contains(p) || get(p) == '#'

        fun move(e: Entity, to: Point) {
            entities.remove(e.pos)
            entities[to] = e
            e.pos = to
        }

        fun search(from: Point, to: Point): List<Point> {
            val frontier = mutableSetOf<Point>()
            val cameFrom = mutableMapOf<Point, Point>()

            frontier += from

            while (frontier.isNotEmpty()) {
                val current = frontier.firstOrNull()!!
                frontier -= current

                val next = current.neighborsHv()
                        .sorted()
                        .filter { inBounds(it) }
                        .filterNot { cameFrom.containsKey(it) }
                        .filterNot { impassable(it) }

                frontier += next
                next.forEach { cameFrom[it] = current }

                if (cameFrom.containsKey(to)) {
                    break
                }
            }

            if (!cameFrom.containsKey(to)) {
                return listOf()
            }

            var current = to
            val path = mutableListOf(to)

            while (current != from) {
                current = cameFrom[current]!!

                path += current
            }

            path.reverse()

            return path
        }
    }

    fun read(input: List<String>): Grid {
        val entities = input.mapIndexed { y, s ->
            s.mapIndexed { x, c -> Point(x, y) to c }
                    .filter { it.second == 'E' || it.second == 'G' }
        }
                .flatten()
                .map { Entity(it.second, it.first) }

        val map = CharMap.from(input.map { it.map { if (it == 'G' || it == 'E') '.' else it }.joinToString("") })

        return Grid(map, entities.map { it.pos to it }.toMap().toMutableMap())
    }

    override fun part1(): String {
        val grid = read(Inputs.INPUT_4)

        println(grid)

        for(i in 1 .. 3) {
            for(p in grid.points) {
                grid.entities[p]?.turn(grid)
            }
            println(grid)
        }



        return ""
    }

    override fun part2(): Int {
        return 0
    }
}

fun main(args: Array<String>) {
    println(Day15.part1())
}

object Inputs {
    val INPUT_0 = """
#######
#E..G.#
#...#.#
#.G.#G#
#######
    """.split("\n").filterNot { it.trim().isEmpty() }

    val INPUT_1 = """
#######
#.E...#
#.....#
#...G.#
#######
    """.split("\n").filterNot { it.trim().isEmpty() }

    val INPUT_2 = """
#######
#G..#E#
#E#E.E#
#G.##.#
#...#E#
#...E.#
#######""".split("\n").filterNot { it.trim().isEmpty() }

    val INPUT_4 = """
#########
#G..G..G#
#.......#
#.......#
#G..E..G#
#.......#
#.......#
#G..G..G#
#########
    """.split("\n").filterNot { it.trim().isEmpty() }
}