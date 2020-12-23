package com.nibado.projects.advent.collect

class CircularList<T> private constructor(private val list: MutableList<T>): Collection<T> {
    constructor() : this(mutableListOf())
    constructor(collection: Iterable<T>) : this(collection.toMutableList())
    constructor(vararg items: T) : this(items.toMutableList())
    private var current = 0

    val currentIndex: Int
        get() = current

    fun current() : T = list[current]
    fun next() : T = list[(current + 1) % list.size]
    fun next(n: Int = 1) : T = list[(current + n) % list.size]

    fun get(index: Int) : T = list[index % list.size]

    override val size: Int = list.size
    override fun contains(element: T) = list.contains(element)
    override fun containsAll(elements: Collection<T>) = list.containsAll(elements)
    override fun isEmpty() = list.isEmpty()
    override fun iterator() = list.iterator()

    fun left(steps: Int) : Int {
        val steps = steps % list.size
        current = (list.size + (current - steps)) % list.size

        return current
    }

    fun counterClockwise(steps: Int) = left(steps)

    fun right(steps: Int) : Int {
        current = (current + steps) % list.size

        return current
    }

    fun clockwise(steps: Int) = right(steps)

    fun seek(index: Int) : Int {
        if(index < 0) {
            throw IllegalArgumentException("$index can't be < 0")
        }
        current = index % list.size

        return current
    }

    fun remove(amount: Int) : List<T> {
        val atEnd = (0 until amount).count { current + it < list.size }
        val atStart = amount - atEnd

        val result =  (0 until atEnd).map { list.removeAt(current) } +
                (0 until atStart).map { list.removeAt(0) }

        return result
    }

    fun insertAfter(elements: List<T>) {
        list.addAll((current + 1 % list.size), elements)
    }

    fun insertAfter(index: Int, elements: List<T>) {
        list.addAll((index + 1 % list.size), elements)
    }

    fun insertBefore(elements: List<T>) {
        list.addAll(current, elements)
    }

    override fun toString(): String = list.mapIndexed { index, t -> if(index == current) "($t)" else "$t" }
            .joinToString(" ", "[", "]")
}