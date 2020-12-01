package com.nibado.projects.advent.graph

class Graph<N, E> {
    private val nodes = mutableMapOf<N, Node<N>>()
    private val nodeToEdge = mutableMapOf<N, MutableSet<Edge<N, E>>>()

    val size: Int
            get() = nodes.size

    data class Node<N>(val key: N)
    data class Edge<N, E>(val value: E, val to: Node<N>)

    fun add(from: N, to: N, edge: E, bidirectional: Boolean = false) : Node<N> {
        val fromNode = nodes.computeIfAbsent(from) { Node(it) }
        nodes.computeIfAbsent(to) { Node(it) }

        setEdge(from, to, edge)

        if(bidirectional) {
            setEdge(to, from, edge)
        }

        return fromNode
    }

    private fun setEdge(from: N, to: N, e: E) : Edge<N, E> {

        val edge = Edge(e, get(to))

        nodeToEdge.computeIfAbsent(from) { mutableSetOf() } += edge

        return edge
    }

    fun get(key: N) = nodes.getValue(key)

    fun edges(key: N) : Set<Edge<N, E>> = nodeToEdge[key] ?: emptySet()
}