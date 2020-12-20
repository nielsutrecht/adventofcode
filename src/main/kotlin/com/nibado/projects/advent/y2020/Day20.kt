package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*

object Day20 : Day {
    private val values = resourceStrings(2020, 20).map { tile ->
        tile.split("\n")
                .let { Tile(it.first().replace("[^0-9]".toRegex(), "").toInt(), Grid(it.drop(1))) }
    }

    private val solution: Map<Point, TilePosition> by lazy {
        val map = mapOf(Point(0, 0) to TilePosition(values.first(), 0, Point(0, 0)))

        solve(map, values.drop(1))
    }

    override fun part1(): Long = solution.keys.bounds()
                .let { (min, max) -> listOf(min, max, Point(min.x, max.y), Point(max.x, min.y)) }
                .map { solution[it]!!.tile.id.toLong() }.reduce { a, b -> a * b }

    private fun solve(map: Map<Point, TilePosition>, available: List<Tile>): Map<Point, TilePosition> {
        if (available.isEmpty()) {
            return map
        }
        val openSpots = map.keys.flatMap { it.neighborsHv() }.filterNot { map.containsKey(it) }

        for (p in openSpots) {
            val neighbors = p.neighborsHv().mapNotNull { map[it] }
            for (tile in available) {
                for (orientation in tile.grids.indices) {
                    if (neighbors.all { tile.grids[orientation].fits(it.grid, p.directionTo(it.point)) }) {
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

    override fun part2(): Int {
        val (min, max) = solution.keys.bounds()

        val width = values.first().grids.first().grid.size
        val size = (max.x - min.x + 1) * (width - 2)

        val largeGrid = Array(size) { Array(size) { ' ' } }

        (min to max).points().forEach { p ->
            val xO = (p.x - min.x) * (width - 2)
            val yO = (p.y - min.y) * (width - 2)

            val grid = solution[p]!!.grid

            grid.grid.drop(1).dropLast(1).forEachIndexed { gy, s ->
                s.drop(1).dropLast(1).forEachIndexed { gx, c ->
                    largeGrid[yO + gy][xO + gx] = c
                }
            }
        }

        val (monsterGrid, points) = Grid(largeGrid).rotations().map { grid ->
            grid to grid.grid.indices.points().filter { p -> SEA_MONSTER.map { it + p }
                    .all { it.inBound(grid.grid.indices, grid.grid.indices) && grid.grid[it.y][it.x] == '#' }
            }.toList()

        }.first { it.second.isNotEmpty() }

        val monsterRough = points.flatMap { p -> SEA_MONSTER.map { it + p } }.toSet()

        return monsterGrid.grid.mapIndexed { y, s -> s.mapIndexedNotNull {x, c -> if(c == '#') Point(x, y) else null } }
                .flatten()
                .count { !monsterRough.contains(it) }
    }

    data class Tile(val id: Int, val grids: List<Grid>) {
        constructor(id: Int, grid: Grid) : this(id, grid.rotations())
    }

    data class Grid(val grid: List<String>) {
        constructor(grid: Array<Array<Char>>) : this(grid.map { it.joinToString("") })

        private val edgeTop = grid.first()
        private val edgeBottom = grid.last()
        private val edgeLeft = grid.map { it.first() }.joinToString("")
        private val edgeRight = grid.map { it.last() }.joinToString("")

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

            repeat(times) {
                input.indices.points().forEach { (x,y) ->
                    array[y][x] = input[input.size - x - 1][y]
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
    }

    data class TilePosition(val tile: Tile, val or: Int, val point: Point) {
        val grid = tile.grids[or]
    }

    private val SEA_MONSTER = """
                  # 
#    ##    ##    ###
 #  #  #  #  #  #
    """.split("\n").filterNot { it.isBlank() }
            .mapIndexed { y, s -> s.mapIndexedNotNull { x, c -> if(c == '#') Point(x,y) else null } }.flatten()
}
