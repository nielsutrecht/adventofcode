package com.nibado.projects.advent.collect

import com.nibado.projects.advent.Point
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

internal class NumberGridTest {
    @Test
    fun `Should be able to read grid from strings`() {
        val grid = NumberGrid.from<Int>(INPUT_4X4)
        assertThat(grid[0, 0]).isEqualTo(1)
        assertThat(grid[3, 0]).isEqualTo(4)
        assertThat(grid[0, 3]).isEqualTo(4)
        assertThat(grid[3, 3]).isEqualTo(7)
    }

    @Test
    fun `Should throw IndexOutOfBounds exception`() {
        val grid = NumberGrid.from<Int>(INPUT_4X4)
        val ex1 = assertThrows<IndexOutOfBoundsException> { grid[5, 5] }
        assertThat(ex1.message).isEqualTo("Index 5, 5 out of bounds 0..3, 0..3")
        val ex2 = assertThrows<IndexOutOfBoundsException> { grid[-1, -1] }
        assertThat(ex2.message).isEqualTo("Index -1, -1 out of bounds 0..3, 0..3")
    }

    @Test
    fun `Should be able to convert to a Graph`() {
        val grid = NumberGrid.from<Int>(INPUT_4X4)
        val graph = grid.toGraph()

        assertThat(graph.nodes()).containsAll(grid.points.toSet())
        assertThat(graph.nodes()).hasSize(grid.points.count())

        listOf(P00 to P10, P10 to P00, P10 to P11, P11 to P01).forEach { (from, to) ->
            assertThat(graph.hasEdge(from, to)).isTrue()
            assertThat(graph[from, to].value).isEqualTo(grid[to])
        }
    }

    companion object {
        private val INPUT_4X4 = listOf("1234", "2345", "3456", "4567")
        private val P00 = Point(0, 0)
        private val P10 = Point(1, 0)
        private val P01 = Point(0, 1)
        private val P11 = Point(1, 1)
    }
}