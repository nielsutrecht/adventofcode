package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Direction
import com.nibado.projects.advent.Point
import com.nibado.projects.advent.resourceStrings
import kotlin.math.sqrt

object Day20 : Day {
    private val values = resourceStrings(2020, 0).map { tile -> tile.split("\n")
            .let { Tile(it.first().replace("[^0-9]".toRegex(), "").toInt(), Grid(it.drop(1))) }}

    override fun part1() = solve()

    fun solve() {
        val widthHeight = sqrt(values.size.toDouble()).toInt()
        if (widthHeight * widthHeight != values.size) {
            throw IllegalArgumentException()
        }

        val points = (0 until widthHeight).flatMap { y -> (0 until widthHeight).map { x-> Point(x, y) } }
        val map = mapOf<Point, TilePosition>()

        val result = solve(map, points, values)

        println(result)
    }

    fun solve(map: Map<Point, TilePosition>, points: List<Point>, available: List<Tile>): Map<Point, TilePosition> {
        if(points.isEmpty()) {
            return map
        }

        for(p in points) {
            val valid = findPossibleTiles(map, p, available)

            for(tilePos in valid) {
                val result = solve(map + (tilePos.point to tilePos),
                        points - tilePos.point,
                        available - tilePos.tile)

                if(result.isNotEmpty()) {
                    println(result)
                    return result
                }
            }
        }

        return emptyMap()
    }

    fun findPossibleTiles(map: Map<Point, TilePosition>, point: Point, available: List<Tile>) : List<TilePosition> {
        val neighbors = point.neighborsHv().mapNotNull { map[it] }
        val tileOrientations = available.asSequence().flatMap {
            tile -> tile.grids.indices.asSequence().map { tile to it } }

        if(neighbors.isEmpty()) {
            return tileOrientations.map { (tile, or) -> TilePosition(tile, or, point) }.toList()
        }

        return tileOrientations.filter { (tile, or) ->
            neighbors.all { fitsWith(it, point, tile.grids[or]) }
        }.map { (tile, or) -> TilePosition(tile, or, point) }.toList()
    }

    fun fitsWith(pos: TilePosition, p:Point, grid: Grid) = grid.fits(pos.grid, p.directionTo(pos.point))

    override fun part2() = TODO()

    data class Tile(val id: Int, val grids: List<Grid>) {
        constructor(id: Int, grid: Grid) : this(id, listOf(
                grid,
                grid.rotate(1),
                grid.rotate(2),
                grid.rotate(3),
                grid.flipH(),
                grid.flipV(),
                grid.rotate(1).flipH(),
                grid.rotate(1).flipV()
        ))
    }

    data class Grid(val grid: List<String>) {
        constructor(vararg grid: String) : this(grid.toList())
        init {
            if(grid.any { it.length != grid.size }) {
                throw IllegalArgumentException("Grid should be square")
            }
        }

        val edgeTop = grid.first()
        val edgeBottom = grid.last()
        val edgeLeft = grid.map { it.first() }.joinToString("")
        val edgeRight = grid.map { it.last() }.joinToString("")

        fun fits(other: Grid, dir: Direction) : Boolean =
            when(dir) {
                Direction.NORTH -> edgeTop == other.edgeBottom
                Direction.SOUTH -> edgeBottom == other.edgeTop
                Direction.WEST -> edgeLeft == other.edgeRight
                Direction.EAST -> edgeRight == other.edgeLeft
            }

        fun flipH() = Grid(grid.reversed())
        fun flipV() = Grid(grid.map { it.reversed() })

        fun rotate(times: Int = 1): Grid {
            var input = grid
            val array = Array(input.size)  { Array(input.size) { '0' } }

            for(i in 0 until times) {
                for (y in input.indices) {
                    for (x in input.indices) {
                        array[y][x] = input[input.size - x - 1][y]
                    }
                }
                input = array.map { it.joinToString("") }
            }
            return Grid(input)
        }

        override fun toString() = grid.joinToString("\n")
    }

    data class TilePosition(val tile: Tile, val or: Int, val point: Point) {
        val grid = tile.grids[or]
    }
}

fun main() {

}

