package com.nibado.projects.advent.collect

import com.nibado.projects.advent.Point

class Maze(val width: Int, val height: Int) {
    companion object {
        val IMPASSIBLE = Float.POSITIVE_INFINITY
        val UNIT = 1.0f
    }

    private val array = FloatArray(width * height)

    fun index(x: Int, y: Int) = x + y * width

    fun get(x: Int, y: Int) = array[index(x, y)]
    fun get(p: Point) = get(p.x, p.y)

    fun set(x: Int, y: Int, value: Float) {
        array[index(x, y)] = value
    }

    fun set(x: Int, y: Int, wall: Boolean) {
        set(x, y, if(wall) IMPASSIBLE else UNIT)
    }

    fun isWall(x: Int, y: Int) = get(x, y) == IMPASSIBLE
    fun isWall(p: Point) = get(p) == IMPASSIBLE

    private fun assertInBounds(vararg points: Point) {
        points.forEach {
            if(!inBound(it)) {
                throw IllegalArgumentException("Point $it not in bounds $width, $height")
            }
        }
    }

    fun dfs(from: Point, to: Point) : List<Point> {
        assertInBounds(from, to)
        return dfs(to, from, mutableSetOf())
    }

    private fun dfs(to: Point, current: Point, visited: MutableSet<Point>) : List<Point> {
        if(current == to) {
            return listOf(current)
        }

        visited += current

        neighbors(current).filterNot { isWall(it) }.filterNot { visited.contains(it) }.forEach {
            val result = dfs(to, it, visited)
            if(result.isNotEmpty()) {
                return listOf(current) + result
            }
        }

        return listOf()
    }

    fun aStar(from: Point, to: Point) : List<Point> {
        assertInBounds(from, to)
        return aStar(from, to, from, mutableSetOf())
    }

    private fun aStar(from: Point, to: Point, current: Point, visited: MutableSet<Point>) : List<Point> {
        if(current == to) {
            return listOf(current)
        }

        visited += current

        println("$current ${from.manhattan(current)} ${current.manhattan(to)}")

        val toVisit = neighbors(current)
                .filterNot { isWall(it) }
                .filterNot { visited.contains(it) }
                .map { Pair(it, from.manhattan(current) + current.manhattan(it)) }
                .sortedBy { it.second }


        toVisit.map { it.first }.forEach {
            val result = aStar(from,    to, it, visited)
            if(result.isNotEmpty()) {
                return listOf(current) + result
            }
        }

        return listOf()
    }

    fun neighbors(point: Point) = point.neighbors().filter { inBound(it) }

    fun inBound(point: Point) = point.inBound(width - 1, height - 1)

    fun print(path: Set<Point> = setOf()) {
        for(y in 0 until height) {
            for (x in 0 until width) {
                if(path.contains(Point(x, y))) {
                    print('O')
                } else {
                    print(if (array[index(x, y)] == IMPASSIBLE) '#' else '.')
                }
            }
            println()
        }
    }
}