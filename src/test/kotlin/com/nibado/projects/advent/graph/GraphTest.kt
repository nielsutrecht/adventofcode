package com.nibado.projects.advent.graph

import com.nibado.projects.advent.collect.NumberGrid
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

internal class GraphTest {
    @Test
    fun `Should be able to add nodes`() {
        val graph = Graph<String, Int>()

        with(graph) {
            add("A", "B", 1)
            add("B", "C", 1)
        }

        assertThat(graph).hasSize(3)

        val expectedNodeKeys = listOf("A", "B", "C")

        assertThat(graph.containsAll(expectedNodeKeys)).isTrue()
        expectedNodeKeys.forEach { assertThat(graph.contains(it)).isTrue() }
    }

    @Test
    fun `Should be able to get edges`() {
        val graph = Graph<String, Int>()

        graph.add("A", "B", 1, true)
        graph.add("B", "C", 1, true)

        assertThat(graph.edges("B")).hasSize(2)
    }

    @Test
    fun `Should throw exception accessing node that doesn't exist`() {
        val graph = Graph<String, Int>()

        val ex = assertThrows<NoSuchElementException> { graph["X"] }

        assertThat(ex.message).isEqualTo("No node with key 'X'")
    }

    @Test
    fun `Should be able to convert from NumberGrid`() {
        val grid = listOf("1234", "2345", "3456", "4567").let { NumberGrid.from<Int>(it) }
        assertThat(grid[0,0]).isEqualTo(1)
        assertThat(grid[0,0]).isEqualTo(1)
        assertThat(grid[0,0]).isEqualTo(1)
        assertThat(grid[0,0]).isEqualTo(1)
    }
}
