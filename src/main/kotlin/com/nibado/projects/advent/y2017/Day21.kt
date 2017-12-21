package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Point
import com.nibado.projects.advent.resourceLines

object Day21 : Day {
    private val input = resourceLines(21).map { it.split(" => ") }.map { Square(it[0].split("/")) to Square(it[1].split("/")) }
    private val two = input.filter { it.first.square.size == 2 }.map { it.first.rotations() to it.second }
    private val three = input.filter { it.first.square.size == 3 }.map { it.first.rotations() to it.second }
    private val start = Square(listOf(".#.", "..#", "###"))

    override fun part1() = solve(5)
    override fun part2() = solve(18)

    fun solve(iterations: Int): String {
        var field = Field(3)
        field.write(start, Point(0, 0))
        (1..iterations).forEach {
            val size = if (field.size % 2 == 0) 2 else 3
            val newField = Field((size + 1) * field.size / size)

            for (y in 0 until field.size / size) {
                for (x in 0 until field.size / size) {
                    val match = (if (size == 3) three else two).filter { it.first.any { field.matches(it, Point(x * size, y * size)) } }
                    newField.write(match.first().second, Point(x * (size + 1), y * (size + 1)))
                }
            }

            field = newField
        }

        return field.count().toString()
    }

    data class Field(val size: Int) {
        val field = (0 until size).map { "X".repeat(size).toCharArray() }

        fun write(square: Square, p: Point) = square.points().forEach {
            val np = p.add(it.x, it.y)
            field[np.y][np.x] = square.square[it.y][it.x]
        }

        fun matches(square: Square, p: Point) = square.points().none {
            val np = p.add(it.x, it.y)
            field[np.y][np.x] != square.square[it.y][it.x]
        }

        fun count() = field.sumBy { it.count { it == '#' } }
    }

    data class Square(val square: List<String>) {
        fun points() = (0 until square.size).flatMap { x -> (0 until square.size).map { y -> Point(x, y) } }
        fun flipV() = Square(square.map { it.reversed() })
        fun flipH() = if (square.size == 2) Square(listOf(square[1], square[0])) else Square(listOf(square[2], square[1], square[0]))

        fun rot90(): Square {
            val chars = square.map { it.toCharArray() }

            points().forEach {
                val x = square.size - 1 - it.y
                chars[it.x][x] = square[it.y][it.x]
            }

            return Square(chars.map { String(it) })
        }

        fun rotations(): Set<Square> {
            val r90 = rot90()
            val r180 = r90.rot90()
            val r270 = r180.rot90()

            return listOf(this, flipV(), flipH(), r90, r180, r270, r90.flipH(), r90.flipV(), r180.flipH(), r180.flipV()).toSet()
        }
    }
}