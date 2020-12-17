package com.nibado.projects.advent.graph

class Graph<N, E> : Collection<N> {
    private val nodes = mutableMapOf<N, Node<N>>()
    private val nodeToEdge = mutableMapOf<N, MutableSet<Edge<N, E>>>()

    override val size: Int
        get() = nodes.size

    data class Node<N>(val key: N)
    data class Edge<N, E>(val value: E, val to: Node<N>)

    fun addAll(vararg pairs: Pair<N, N>, edge: E) {
        pairs.forEach { (a, b) -> add(a, b, edge) }
    }

    fun add(from: N, to: N, edge: E, bidirectional: Boolean = false): Node<N> {
        val fromNode = nodes.computeIfAbsent(from) { node(it) }
        nodes.computeIfAbsent(to) { node(it) }

        setEdge(from, to, edge)

        if (bidirectional) {
            setEdge(to, from, edge)
        }

        return fromNode
    }

    private fun setEdge(from: N, to: N, e: E): Edge<N, E> {
        val edge = edge(e, get(to))

        nodeToEdge.computeIfAbsent(from) { mutableSetOf() } += edge

        return edge
    }

    operator fun get(key: N) = nodes[key] ?: throw NoSuchElementException("No node with key '$key'")

    fun edges(key: N): Set<Edge<N, E>> = nodeToEdge[key] ?: emptySet()
    fun edges(node: Node<N>) = edges(node.key)
    fun nodes(key: N) = edges(key).map { it.to }
    fun nodes(node: Node<N>) = edges(node).map { it.to }

    override fun contains(element: N) = nodes.keys.contains(element)
    override fun containsAll(elements: Collection<N>) = nodes.keys.containsAll(elements)
    override fun isEmpty() = nodes.isEmpty()
    override fun iterator() = nodes.keys.iterator()

    companion object {
        fun <T> node(value: T) = Node(value)
        fun <E, N> edge(value: E, node: Node<N>) = Edge(value, node)
        fun <E, N> edge(value: E, node: N) = Edge(value, node(node))
    }
}
