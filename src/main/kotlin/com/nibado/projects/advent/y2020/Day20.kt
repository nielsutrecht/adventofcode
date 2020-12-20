package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Direction
import com.nibado.projects.advent.Point
import com.nibado.projects.advent.resourceStrings

object Day20 : Day {
    private val values = resourceStrings(2020, 20).map { tile ->
        tile.split("\n")
                .let { Tile(it.first().replace("[^0-9]".toRegex(), "").toInt(), Grid(it.drop(1))) }
    }

    private val solution: Map<Point, TilePosition> by lazy {
        val map = mapOf(Point(0, 0) to TilePosition(values.first(), 0, Point(0, 0)))

        solve(map, values.drop(1))
    }

    override fun part1(): Long = solution.let { result ->
        val min = Point(result.keys.map { it.x }.min()!!, result.keys.map { it.y }.min()!!)
        val max = Point(result.keys.map { it.x }.max()!!, result.keys.map { it.y }.max()!!)

        val corners = listOf(min, max, Point(min.x, max.y), Point(max.x, min.y))

        return corners.map { result[it]!!.tile.id.toLong() }.reduce { a, b -> a * b }
    }

    fun solve(): Map<Point, TilePosition> {
        val map = mapOf(Point(0, 0) to TilePosition(values.first(), 0, Point(0, 0)))

        return solve(map, values.drop(1))
    }

    fun solve(map: Map<Point, TilePosition>, available: List<Tile>): Map<Point, TilePosition> {
        if (available.isEmpty()) {
            return map
        }
        val openSpots = map.keys.flatMap { it.neighborsHv() }.filterNot { map.containsKey(it) }

        for (p in openSpots) {
            val neighbors = p.neighborsHv().mapNotNull { map[it] }
            for (tile in available) {
                for (orientation in tile.grids.indices) {
                    if (neighbors.all { fitsWith(it, p, tile.grids[orientation]) }) {
                        val result = solve(map + (p to TilePosition(tile, orientation, p)), available - tile)
                        if (result.isNotEmpty()) {
                            return result
                        }
                    }
                }
            }
        }

        return emptyMap()
    }

    fun fitsWith(pos: TilePosition, p: Point, grid: Grid) = grid.fits(pos.grid, p.directionTo(pos.point))

    override fun part2(): Long {
        val (min, max) = solution.let { result ->
            val min = Point(result.keys.map { it.x }.min()!!, result.keys.map { it.y }.min()!!)
            val max = Point(result.keys.map { it.x }.max()!!, result.keys.map { it.y }.max()!!)

            min to max
        }

        val width = values.first().grids.first().grid.size
        val size = (max.x - min.x + 1) * (width - 2)

        val largeGrid = Array(size) { Array(size) { ' ' } }

        for(y in min.y .. max.y) {
            for(x in min.x .. max.x) {
                val xO = (x - min.x) * (width - 2)
                val yO = (y - min.y) * (width - 2)

                val grid = solution[Point(x,y)]!!.grid

                grid.grid.drop(1).dropLast(1).forEachIndexed { gy, s ->
                    s.drop(1).dropLast(1).forEachIndexed { gx, c ->
                        largeGrid[yO + gy][xO + gx] = c
                    }
                }
            }
        }


        val monsterPoints = SEA_MONSTER.split("\n").filterNot { it.isBlank() }
                .mapIndexed { y, s -> s.mapIndexedNotNull { x, c -> if(c == '#') Point(x,y) else null } }.flatten()

        fun monsterAt(p: Point, grid: Grid) : Boolean =
            monsterPoints.map { it + p }.all { it.inBound(grid.grid.indices, grid.grid.indices) &&
                    grid.grid[it.y][it.x] == '#'
            }

        val grids = Grid(largeGrid).rotations()

        for(grid in grids) {
            val monsterLocations = mutableListOf<Point>()
            for (y in grid.grid.indices) {
                for (x in grid.grid.indices) {
                    val p = Point(x, y)
                    if (monsterAt(p, grid)) {
                        monsterLocations += p
                    }
                }
            }
        }

        val gridWithMonsters = grids.map { grid ->
            val monsterLocations = mutableListOf<Point>()
            for (y in grid.grid.indices) {
                for (x in grid.grid.indices) {
                    val p = Point(x, y)
                    if (monsterAt(p, grid)) {
                        monsterLocations += p
                    }
                }



            }
            grid to monsterLocations.toList()
        }.filter { it.second.isNotEmpty() }.first()

        val (monsterGrid, points) = gridWithMonsters

        val monsterRough = points.flatMap { p -> monsterPoints.map { it + p } }.toSet()

        val count = monsterGrid.grid.mapIndexed { y, s -> s.mapIndexedNotNull {x, c -> if(c == '#') Point(x, y) else null } }
                .flatten()
                .count { !monsterRough.contains(it) }



        return count.toLong()
    }

    data class Tile(val id: Int, val grids: List<Grid>) {
        constructor(id: Int, grid: Grid) : this(id, grid.rotations())
    }

    data class Grid(val grid: List<String>) {
        constructor(vararg grid: String) : this(grid.toList())
        constructor(grid: Array<Array<Char>>) : this(grid.map { it.joinToString("") })
        init {
            if (grid.any { it.length != grid.size }) {
                throw IllegalArgumentException("Grid should be square")
            }
        }

        val edgeTop = grid.first()
        val edgeBottom = grid.last()
        val edgeLeft = grid.map { it.first() }.joinToString("")
        val edgeRight = grid.map { it.last() }.joinToString("")

        fun fits(other: Grid, dir: Direction): Boolean =
                when (dir) {
                    Direction.NORTH -> edgeTop == other.edgeBottom
                    Direction.SOUTH -> edgeBottom == other.edgeTop
                    Direction.WEST -> edgeLeft == other.edgeRight
                    Direction.EAST -> edgeRight == other.edgeLeft
                }

        fun flipH() = Grid(grid.reversed())
        fun flipV() = Grid(grid.map { it.reversed() })

        fun rotate(times: Int = 1): Grid {
            var input = grid
            val array = Array(input.size) { Array(input.size) { '0' } }

            for (i in 0 until times) {
                for (y in input.indices) {
                    for (x in input.indices) {
                        array[y][x] = input[input.size - x - 1][y]
                    }
                }
                input = array.map { it.joinToString("") }
            }
            return Grid(input)
        }

        fun rotations() = setOf(this,
                rotate(1),
                rotate(2),
                rotate(3),
                flipH(),
                flipV(),
                rotate(1).flipH(),
                rotate(1).flipV()).toList()

        override fun toString() = grid.joinToString("\n")
    }

    data class TilePosition(val tile: Tile, val or: Int, val point: Point) {
        val grid = tile.grids[or]
    }

    const val SEA_MONSTER = """
                  # 
#    ##    ##    ###
 #  #  #  #  #  #
    """
}

fun main() {
    println(Day20.part2())
}

