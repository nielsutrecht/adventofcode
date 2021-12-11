package com.nibado.projects.advent.collect

import com.nibado.projects.advent.Point

class NumberGrid<N : Number>(
    override val width: Int,
    override val height: Int, numbers: Collection<N>) : Grid<N> {

    private val grid = numbers.toMutableList()

    override val elements: List<N>
        get() = grid

    override fun apply(map: (Point, N) -> N) {
        indices.forEach { (x, y) -> grid[toIndex(x, y)] = map(Point(x, y), grid[toIndex(x, y)]) }
    }

    override fun apply(map: (Int, Int, N) -> N) {
        indices.forEach { (x, y) -> grid[toIndex(x, y)] = map(x, y, grid[toIndex(x, y)]) }
    }

    override fun toString() : String = elements
        .chunked(width).joinToString("\n") { it.joinToString("") }

    override fun set(p: Point, value: N) {
        grid[toIndex(p.x, p.y)] = value
    }

    override fun set(x: Int, y: Int, value: N) {
        grid[toIndex(x, y)] = value
    }

    companion object {
        inline fun <reified N: Number> from(lines: List<String>) : NumberGrid<N> {
            val height = lines.size
            val width = lines[0].length

            val numbers = lines.flatMap {
                it.toCharArray().map { it.digitToInt() as N }
            }

            return NumberGrid(width, height, numbers)
        }
    }
}