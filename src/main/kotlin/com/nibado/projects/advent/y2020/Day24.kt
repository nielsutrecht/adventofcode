package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*

object Day24 : Day {

    private val values = resourceLines(2020, 24).map(::parse)
    private val points = listOf("e", "w", "se", "sw", "ne", "nw").map { toPoint(it) }

    private val initial: Map<Point3D, Boolean> by lazy {
        val start = Point3D(0, 0, 0)
        val hexes = mutableMapOf<Point3D, Boolean>()
        hexes[start] = false
        for (t in values) {
            var current = start
            t.forEach { dir ->
                current = current + dir
                hexes.computeIfAbsent(current) { false }
            }
            hexes[current] = !hexes.getOrDefault(current, false)
            points.map { it + current }.forEach { hexes.computeIfAbsent(it) { false } }
        }
        hexes
    }

    private fun parse(line: String): List<Point3D> {
        var i = 0
        val list = mutableListOf<String>()

        while (i < line.length) {
            when {
                line.substring(i).startsWith("e") -> {
                    list.add("e");i += 1
                }
                line.substring(i).startsWith("w") -> {
                    list.add("w");i += 1
                }
                line.substring(i).startsWith("se") -> {
                    list.add("se");i += 2
                }
                line.substring(i).startsWith("sw") -> {
                    list.add("sw");i += 2
                }
                line.substring(i).startsWith("ne") -> {
                    list.add("ne");i += 2
                }
                line.substring(i).startsWith("nw") -> {
                    list.add("nw");i += 2
                }
            }
        }

        return list.map { toPoint(it) }
    }

    fun toPoint(dir: String) = when (dir) {
        "e" -> Point3D(1, -1, 0)
        "w" -> Point3D(-1, 1, 0)
        "se" -> Point3D(0, -1, +1)
        "sw" -> Point3D(-1, 0, 1)
        "ne" -> Point3D(1, 0, -1)
        "nw" -> Point3D(0, 1, -1)
        else -> throw IllegalArgumentException(dir)
    }

    override fun part1(): Int = initial.values.count { it }

    override fun part2() : Int {
        val hexes = initial.toMutableMap()
        repeat(100) { day ->
            val toFlip = hexes.filter { (point, black) ->
                val blackNeighbors = points.count { off -> hexes.getOrDefault(point + off, false) }

                (black && (blackNeighbors == 0 || blackNeighbors > 2)) ||
                        (!black && blackNeighbors == 2)

            }.map { it.key }
            toFlip.forEach {
                hexes[it] = !hexes.getOrDefault(it, false)
            }
            toFlip.flatMap { p -> points.map { p + it } }.forEach { hexes.computeIfAbsent(it) { false } }

            print("${day + 1}: ")
            println(hexes.values.count { it })
        }

        return hexes.values.count { it }
    }
}
    