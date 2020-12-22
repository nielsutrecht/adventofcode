package com.nibado.projects.advent.collect

class Queue<T> private constructor(private val list: MutableList<T>) : Collection<T> {

    constructor() : this(mutableListOf())
    constructor(collection: Iterable<T>) : this(collection.toMutableList())
    constructor(vararg items: T) : this(items.toMutableList())

    override val size: Int
        get() = list.size

    override fun contains(element: T): Boolean = list.contains(element)
    override fun containsAll(elements: Collection<T>): Boolean = list.containsAll(elements)
    override fun isEmpty(): Boolean = list.isEmpty()
    override fun iterator(): Iterator<T> = list.iterator()

    fun add(element: T) {
        list.add(element)
    }

    fun addAll(vararg elements: T) {
        list.addAll(elements)
    }

    fun take(): T = list.removeAt(0)
    fun peek(): T = list.first()

    fun takeOrNull(): T? = if (isEmpty()) null else take()
    fun peekOrNull(): T? = if (isEmpty()) null else peek()

    fun takeInto(collection: MutableCollection<T>) {
        while (isNotEmpty()) {
            collection += take()
        }
    }

    operator fun plusAssign(element: T) {
        add(element)
    }

    override fun toString(): String = list.toString()
}
