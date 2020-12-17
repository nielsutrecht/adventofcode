package com.nibado.projects.advent.graph

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class GraphSearchTest {

    @Test
    fun `Should find 5 reachable nodes from Start`() {
        val graph = Graph<String, Int>()

        graph.addAll(
                "Start" to "A",
                "Start" to "B",
                "A" to "C",
                "B" to "C",
                "B" to "Finish",
                "C" to "Finish",
                "X" to "Y",
                "Y" to "Z",
                edge = 1
        )

        val nodes = GraphSearch.nodes(graph, "Start").map { it.key }

        assertThat(nodes).containsExactly("Start", "A", "B", "C", "Finish")
    }

    @Test
    fun `Should find 3 paths between Start and Finish`() {
        val graph = Graph<String, Int>()

        graph.addAll(
                "Start" to "A",
                "Start" to "B",
                "A" to "C",
                "B" to "C",
                "B" to "Finish",
                "C" to "Finish",
                edge = 1
        )


        val paths = GraphSearch.path(graph, "Start", "Finish")

        println(paths)
    }

    @Test
    fun `Should sum path weights correctly`() {
        val path = GraphSearch.Path(listOf(
                pair("A", 1),
                pair("B", 2),
                pair("C", 3),
                pair("D", 4)
        ))

        assertThat(path.weight { it * 10 }).isEqualTo(100)
    }

    private fun node(n: String) = Graph.node(n)
    private fun edge(e:Int, n: String) = Graph.edge(e, n)
    private fun pair(n: String, e: Int) = Graph.node(n).let { Graph.edge(e, it) to it}
}
