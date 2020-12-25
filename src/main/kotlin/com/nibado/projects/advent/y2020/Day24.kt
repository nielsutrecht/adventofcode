package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*

object Day24 : Day {
    private val values = resourceLines(2020, 24).map(::parse)
    private val points = Hex.Direction.values().map { it.point}

    private val initial: Map<Point3D, Boolean> by lazy {
        val start = Point3D(0, 0, 0)
        val hexes = mutableMapOf<Point3D, Boolean>()
        hexes[start] = false
        for (t in values) {
            var current = start
            t.forEach { dir ->
                current += dir
            }
            hexes[current] = !hexes.getOrDefault(current, false)
        }
        hexes
    }

    private fun parse(line: String): List<Point3D> {
        var i = 0
        val list = mutableListOf<Point3D>()

        while (i < line.length) {
            val found = Hex.Direction.values().first { line.substring(i).startsWith(it.name, true) }
            list += found.point
            i += found.name.length
        }

        return list
    }


    override fun part1(): Int = initial.values.count { it }

    override fun part2() : Int {
        val hexes = initial.toMutableMap()
        hexes.keys.flatMap { Hex.neighbors(it) }.forEach { hexes.computeIfAbsent(it) { false} }
        repeat(100) {
            val toFlip = hexes.filter { (point, black) ->
                val blackNeighbors = points.count { off -> hexes.getOrDefault(point + off, false) }

                (black && (blackNeighbors == 0 || blackNeighbors > 2)) ||
                        (!black && blackNeighbors == 2)

            }.map { it.key }
            toFlip.forEach {
                hexes[it] = !hexes.getOrDefault(it, false)
            }

            toFlip.flatMap { p -> Hex.neighbors(p) }.forEach { hexes.computeIfAbsent(it) { false } }
        }

        return hexes.values.count { it }
    }
}
    