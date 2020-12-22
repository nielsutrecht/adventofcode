package com.nibado.projects.advent.collect

class Stack<T> private constructor(private val list: MutableList<T>): Collection<T> {

    constructor() : this(mutableListOf())
    constructor(collection: Iterable<T>) : this(collection.toMutableList())
    constructor(vararg items: T) : this(items.toMutableList())

    override val size: Int
        get() = list.size

    private val lastIndex: Int
        get() = if(isNotEmpty()) size - 1 else throw IllegalStateException("Stack is empty")

    override fun contains(element: T): Boolean  = list.contains(element)
    override fun containsAll(elements: Collection<T>): Boolean  = list.containsAll(elements)
    override fun isEmpty(): Boolean  = list.isEmpty()
    override fun iterator(): Iterator<T>  = list.reversed().iterator()

    fun push(element: T) {
        list.add(element)
    }

    fun pop() : T = list.removeAt(lastIndex)
    fun peek() : T = list[lastIndex]

    fun popOrNull() : T? = if(isEmpty()) null else pop()
    fun peekOrNull() : T? = if(isEmpty()) null else peek()

    fun popInto(collection: MutableCollection<T>) {
        while(isNotEmpty()) {
            collection += pop()
        }
    }

    operator fun plusAssign(element: T) {
        push(element)
    }
}
