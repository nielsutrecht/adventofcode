package com.nibado.projects.advent.collect

import com.nibado.projects.advent.Point
import com.nibado.projects.advent.Rectangle

class CharMap(val width: Int, val height: Int, fill: Char = ' ', var pen: Char = '#') {
    private val chars = CharArray(width * height) { fill }

    private fun toIndex(p: Point) = toIndex(p.x, p.y)
    private fun toIndex(x: Int, y: Int) = x + y * width

    operator fun get(p: Point) = chars[toIndex(p)]
    operator fun get(x: Int, y: Int) = chars[toIndex(x, y)]

    operator fun set(p: Point, c: Char) {
        chars[toIndex(p)] = c
    }

    operator fun set(x: Int, y: Int, c: Char) {
        chars[toIndex(x, y)] = c
    }

    fun isEq(p: Point, c: Char) = if (!inBounds(p)) {
        false
    } else this[p] == c

    fun count(c: Char) = chars.count { it == c }
    fun count(set: Set<Char>) = chars.count { it in set }
    fun count(f: (Char) -> Boolean) = chars.count { f(it) }

    fun drawHorizontal(p: Point, amount: Int, c: Char = pen) {
        for (i in 0 until amount) {
            this[p.x + i, p.y] = c
        }
    }

    fun drawHorizontal(y: Int, xRange: IntRange, c: Char = pen) {
        for (x in xRange) {
            this[x, y] = c
        }
    }

    fun drawVertical(p: Point, amount: Int, c: Char = pen) {
        for (i in 0 until amount) {
            this[p.x, p.y + i] = c
        }
    }

    fun drawVertical(x: Int, yRange: IntRange, c: Char = pen) {
        for (y in yRange) {
            this[x, y] = c
        }
    }

    fun toString(rectangle: Rectangle) = toString(rectangle.left, rectangle.right)
    fun toString(topLeft: Point) = toString(topLeft, Point(width - 1, height - 1))

    fun toString(topLeft: Point, bottomRight: Point): String {
        val builder = StringBuilder()

        for (y in topLeft.y..bottomRight.y) {
            for (x in topLeft.x..bottomRight.x) {
                builder.append(this[x, y])
            }
            builder.append('\n')
        }
        return builder.toString()
    }

    fun points(f: (Char) -> Boolean = { true }) = (0 until height)
            .flatMap { y -> (0 until width).map { x -> Point(x, y) }}
            .filter { f(this[it]) }

    fun inBounds(p: Point) = inBounds(p.x, p.y)
    fun inBounds(x: Int, y: Int) = x in 0 until width && y in 0 until height

    override fun toString() = toString(Point(0, 0), Point(width - 1, height - 1))
}