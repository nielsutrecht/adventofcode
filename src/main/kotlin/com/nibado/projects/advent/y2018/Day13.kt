package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Direction
import com.nibado.projects.advent.Point
import com.nibado.projects.advent.resourceLines

object Day13 : Day {
    private val charToDirectionMap = mapOf(
            '<' to Direction.WEST,
            '>' to Direction.EAST,
            '^' to Direction.NORTH,
            'v' to Direction.SOUTH)

    private val cornerMap = mapOf(
            '/' to mapOf(
                    Direction.NORTH to Direction.EAST,
                    Direction.WEST to Direction.SOUTH,
                    Direction.EAST to Direction.NORTH,
                    Direction.SOUTH to Direction.WEST),
            '\\' to mapOf(
                    Direction.EAST to Direction.SOUTH,
                    Direction.NORTH to Direction.WEST,
                    Direction.WEST to Direction.NORTH,
                    Direction.SOUTH to Direction.EAST)
    )

    private val grid = resourceLines(2018, 13)

    enum class Turn {
        LEFT,
        STRAIGHT,
        RIGHT;

        fun next() = when (this) {
            LEFT -> STRAIGHT
            STRAIGHT -> RIGHT
            RIGHT -> LEFT
        }
    }

    data class Cart(var p: Point, var direction: Direction, var turn: Turn = Turn.LEFT) : Comparable<Cart> {
        override fun compareTo(other: Cart) = if(this.p.x == other.p.x) this.p.y.compareTo(other.p.y) else this.p.x.compareTo(other.p.x)
    }

    private fun tick(carts: MutableList<Cart>): Set<Point> {
        val crashes = mutableSetOf<Point>()

        carts.sort()

        for (cart in carts.toList()) {
            val nextPoint = cart.p + cart.direction

            if (nextPoint in carts.map { it.p }.toSet()) {
                carts.removeIf { it.p == nextPoint || it == cart }

                crashes += nextPoint

                continue
            }

            cart.p = nextPoint
            val c = grid(nextPoint)

            if (c in cornerMap.keys) {
                cart.direction = cornerMap[c]!![cart.direction]!!
            } else if (c == '+') {
                cart.direction = when (cart.turn) {
                    Turn.LEFT -> cart.direction.ccw()
                    Turn.RIGHT -> cart.direction.cw()
                    Turn.STRAIGHT -> cart.direction
                }
                cart.turn = cart.turn.next()
            }
        }

        return crashes
    }

    private fun grid(p: Point) = grid[p.y][p.x]

    private fun carts(grid: List<String>) = gridPoints(grid).filter { grid(it) in charToDirectionMap.keys }
            .map { Cart(it, charToDirectionMap[grid[it.y][it.x]]!!) }

    private fun gridPoints(grid: List<String>) = grid.indices.flatMap { y -> grid[y].indices.map { x -> Point(x, y) } }

    override fun part1(): String {
        val carts = carts(grid).toMutableList()

        while(true) {
            val crashLocation = tick(carts)
            if (crashLocation.isNotEmpty()) {
                return crashLocation.first().let { "${it.x},${it.y}" }
            }
        }
    }

    override fun part2(): String {
        val carts = carts(grid).toMutableList()

        while(true) {
            tick(carts)

            if (carts.size <= 1) {
                return carts.first().let { "${it.p.x},${it.p.y}" }
            }
        }
    }
}