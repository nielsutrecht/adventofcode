package com.nibado.projects.advent.collect

class Tree<E> (val value : E?, private val parent: Tree<E>? = null) {
    val nodes = mutableListOf<Tree<E>>()
    fun root() = parent == null
    fun leaf() = nodes.isEmpty()

    fun add(tree: Tree<E>) {
        nodes += tree
    }

    fun add(value: E) {
        nodes += Tree(value, this)
    }

    fun path(): List<Tree<E>> {
        return if(root()) {
            listOf(this)
        } else {
            parent!!.path() + this
        }
    }
}