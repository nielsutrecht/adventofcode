package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.*
import com.nibado.projects.advent.collect.NumberGrid
import com.nibado.projects.advent.search.Dijkstra

object Day15 : Day {
    val grid = resourceLines(2021, 15).let { NumberGrid.from<Int>(it) }


    fun search() = Dijkstra.search(grid, grid.bounds.left, grid.bounds.right)[grid.bounds.right]!!.second.toInt()




    override fun part1() =  Dijkstra.search(grid, grid.bounds.left, grid.bounds.right)[grid.bounds.right]!!.second.toInt()

    override fun part2() : Int {
        //println(grid)
        val tiled = NumberGrid<Int>(grid.width * 5, grid.height * 5, 0)
        grid.points.forEach {
            (0 .. 4).forEach { scaleY ->
                (0..4).forEach { scaleX ->
                    val newPoint = Point(it.x + (scaleX * grid.width), it.y + (scaleY * grid.height))
                    val newValue = (grid[it] + scaleX + scaleY).let { if(it > 9) it - 9 else it }
                    tiled[newPoint] = newValue
                }
            }
        }
       // println(tiled)
        return Dijkstra.search(tiled, tiled.bounds.left, tiled.bounds.right)[tiled.bounds.right]!!.second.toInt()
    }
}

fun main() {
    //println(Day15.grid)
    println(Day15.part2())
}