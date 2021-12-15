package com.nibado.projects.advent.search

import com.nibado.projects.advent.*
import com.nibado.projects.advent.collect.NumberGrid
import com.nibado.projects.advent.graph.Graph
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import kotlin.system.measureTimeMillis

internal class DijkstraTest {
    val input = TestResource.resourceLines("numbergrid-search.txt")

    @Test
    fun gridPath() {
        val path = Dijkstra.shortestPath(grid, grid.bounds.left, grid.bounds.right)

        assertThat(path.size).isEqualTo(19)
        assertThat(path.sumOf { grid[it] }).isEqualTo(41)
    }

    @Test
    fun `Should return the shortest path in a simple graph`() {
        val graph = Graph<String, Int>()
        graph.add("A", "B", 10)
        graph.add("B", "C", 10)
        graph.add("A", "C", 21)

        val path = Dijkstra.shortestPath(graph, "A", "C")
        assertThat(path).containsExactly("A", "B", "C")
    }

    @Test
    fun `Should return a path`() {
        val graph = NumberGrid.from<Int>(input).toGraph()

        val path = Dijkstra.shortestPath(graph, grid.bounds.left, grid.bounds.right)
        assertThat(path.first()).isEqualTo(grid.bounds.left)
        assertThat(path.last()).isEqualTo(grid.bounds.right)
        assertThat(path.size).isEqualTo(19)
        assertThat(path.sumOf { grid[it] }).isEqualTo(41)
    }

    @Test
    @Disabled
    fun performance() {
        val timeGrid = measureTimeMillis {
            Dijkstra.shortestPath(tiled, tiled.bounds.left, tiled.bounds.right)
        }
        val tiledGraph = tiled.toGraph()
        val timeGraph= measureTimeMillis {
            Dijkstra.shortestPath(tiledGraph, tiled.bounds.left, tiled.bounds.right)
        }

        println("$timeGrid - $timeGraph")
    }

    companion object {
        val grid = NumberGrid.from<Int>(resourceLines(2021, 15))
        val tiled = NumberGrid(grid.width * 5, grid.height * 5, 0).also { tiled ->
            grid.points.forEach {
                (0 .. 4).forEach { scaleY ->
                    (0..4).forEach { scaleX ->
                        val newPoint = Point(it.x + (scaleX * grid.width), it.y + (scaleY * grid.height))
                        val newValue = (grid[it] + scaleX + scaleY).let { if(it > 9) it - 9 else it }
                        tiled[newPoint] = newValue
                    }
                }
            }
        }
    }
}