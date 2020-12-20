package com.nibado.projects.advent.collect

import com.nibado.projects.advent.Point
import com.nibado.projects.advent.Rectangle
import javax.swing.text.html.HTML.Attribute.N

class CharMap private constructor(
        val width: Int,
        val height: Int,
        var pen: Char = '#',
        private val chars: CharArray) {

    constructor(width: Int,
                height: Int,
                fill: Char = ' ',
                pen: Char = '#') : this(
            width,
            height,
            pen,
            CharArray(width * height) { fill })

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

    fun clone() = CharMap(width, height, pen, chars.clone())

    fun rotate90() : CharMap {
        val newMap = clone()

        for (i in 0 until width) {
            for (j in 0 until width) {
                newMap[j, width - i - 1] = this[i, j]
            }
        }

        return newMap
    }

    fun row(row: Int) : List<Char> = (0 until width).map { this[it, row] }
    fun rowFirst() = row(0)
    fun rowLast() = row(height - 1)
    fun column(column: Int) : List<Char> = (0 until height).map { this[column, it] }
    fun columnFirst() = column(0)
    fun columnLast() = column(width - 1)

    fun flipH() : CharMap {
        val new = clone()

        for(y in 0 until height) {
            for(x in 0 until width) {
                new[x, y] = this[width - x - 1, y]
            }
        }

        return new
    }

    fun flipV() : CharMap {
        val new = clone()

        for(y in 0 until height) {
            for(x in 0 until width) {
                new[x, y] = this[x, height - y - 1]
            }
        }

        return new
    }

    companion object {
        fun from(lines: List<String>) : CharMap {
            val input = lines.map { it.trim() }.filterNot { it.isEmpty() }

            if(input.isEmpty()) {
                throw IllegalArgumentException("List is empty")
            }

            val height = input.size
            val width = input.first().length

            if(input.any { it.length != width }) {
                throw IllegalArgumentException("All lines in input should be width $width")
            }

            val map = CharMap(width, height)

            input.indices.forEach { y ->
                input[y].indices.forEach { x ->
                    map[x, y] = input[y][x]
                }
            }

            return map
        }
        fun from(s: String) = from(s.split('\n').map { it.trim() })
        fun withSize(charMap: CharMap) = CharMap(charMap.width, charMap.height)
    }
}
