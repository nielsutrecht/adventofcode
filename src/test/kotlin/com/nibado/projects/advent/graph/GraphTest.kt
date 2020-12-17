package com.nibado.projects.advent.graph

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

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
}
