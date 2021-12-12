package com.nibado.projects.advent.graph

class Graph<N, E> : Collection<N> {
    private val nodes: MutableMap<N, Node<N>>
    private val nodeToEdge : MutableMap<N, MutableSet<Edge<N, E>>>

    private constructor(nodes: MutableMap<N, Node<N>>, nodeToEdge: MutableMap<N, MutableSet<Edge<N, E>>>) {
        this.nodes = nodes
        this.nodeToEdge = nodeToEdge
    }

    constructor() : this(mutableMapOf<N, Node<N>>(), mutableMapOf<N, MutableSet<Edge<N, E>>>())

    /**
     * Copy constructor
     */
    constructor(graph: Graph<N, E>) : this(graph.nodes.toMap().toMutableMap(), graph.nodeToEdge.toMap().toMutableMap())

    override val size: Int
        get() = nodes.size

    data class Node<N>(val key: N)
    data class Edge<N, E>(val value: E, val to: Node<N>)

    fun addAll(vararg pairs: Pair<N, N>, edge: E, bidirectional: Boolean = false) : Graph<N, E>  {
        pairs.forEach { (a, b) -> add(a, b, edge, bidirectional) }
        return this
    }

    fun addAll(pairs: Collection<Pair<N, N>>, edge: E, bidirectional: Boolean = false) : Graph<N, E>  {
        pairs.forEach { (a, b) -> add(a, b, edge, bidirectional) }
        return this
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

    /**
     * Searches for all paths from 'from' to 'to' using the provided strategy and a provided node filter.
     *
     * See Year 2021 Day 12 for usage.
     */
    fun paths(
        from: N,
        to: N,
        strategy: SearchStrategy = SearchStrategy.DEPTH_FIRST,
        nodeFilter: (Node<N>, Map<Node<N>, Int>) -> Boolean) : List<List<Node<N>>>  =
        when(strategy) {
            SearchStrategy.DEPTH_FIRST -> mutableListOf<List<Node<N>>>().also { depthFirst(this[from], this[to], emptyMap(), emptyList(), it, nodeFilter) }
        }

    //TODO: Implement with stack instead of recursion.
    private fun depthFirst(
        from: Node<N>,
        to: Node<N>,
        visited: Map<Node<N>, Int>,
        path: List<Node<N>>,
        paths: MutableList<List<Node<N>>>,
        nodeFilter: (Node<N>, Map<Node<N>, Int>) -> Boolean)  {
        if (from == to) {
            paths += path
            return
        }

        val newVisited = visited + (from to visited.getOrDefault(from, 0) + 1)

        nodes(from).filter { nodeFilter(it, newVisited) }.forEach {
            depthFirst(it, to, newVisited, path + it, paths, nodeFilter)
        }
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

    enum class SearchStrategy {
        DEPTH_FIRST
    }
}
