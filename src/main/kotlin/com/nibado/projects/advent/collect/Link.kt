package com.nibado.projects.advent.collect

class Link<T>(val value: T, var next: Link<T>? = null) : Collection<T> {

    fun next() = next ?: this
    fun nextOrNull() = next

    fun firstLink() = linkSequence().first()
    fun lastLink() = linkSequence().last()

    fun addNext(value: T) : Link<T> = addNext(Link(value))

    fun addNext(link: Link<T>) : Link<T> {
        val next = this.next
        link.lastLink().next = next
        this.next = link

        return link
    }

    fun addAll(iterable: Iterable<T>) {
        val firstLink = Link(iterable.first())
        val last = iterable.drop(1).fold(firstLink) {
            link, e -> link.next = Link(e)
            link.next!!
        }

        last.next = this.next
        this.next = firstLink
    }

    fun remove(amount: Int = 1) : Link<T> {
        val toRemove = linkSequence().drop(amount).first()
        val removeStart = next()
        next = toRemove.next
        toRemove.next = null

        return removeStart
    }

    override val size: Int
        get() = linkSequence().count()

    override fun contains(element: T): Boolean =
        linkSequence().any { it.value == element }

    override fun containsAll(elements: Collection<T>): Boolean {
        return this.toSet().containsAll(elements)
    }

    override fun isEmpty(): Boolean  = false

    override fun iterator(): Iterator<T> =
        linkSequence().map { it.value }.iterator()

    fun linkSequence() : Sequence<Link<T>> {
        val start = this
        return sequence {
            var current : Link<T>? = start
            while(current != null) {
                yield(current!!)
                current = current.next
                if(current == start) {
                    break
                }
            }
        }
    }

    override fun toString() : String = joinToString(", ", "[", "]")

    companion object {
        fun <T> of(iterable: Iterable<T>) : Link<T> = Link(iterable.first()).also { it.addAll(iterable.drop(1)) }
        fun <T> of(vararg elements: T) = of(elements.toList())
    }
}