package com.nibado.projects.advent.collect

import org.junit.jupiter.api.Test

internal class LinkTest {
    @Test
    fun `Can create`() {
        val link = Link(1)

        println(link)

        link.addNext(4).addNext(5)
        println(link)
        link.addAll(listOf(2, 3))
        println(link)

        //val removed = link.remove(3)

        //println(removed)
        //println(link)

        link.lastLink().next = link
        println(link)
        println(link.next())
        println(link.next().next())
    }

    @Test
    fun `Can remove`() {
        val link = Link.of(1, 2, 3, 4, 5, 6)
        println(link)

        val removed = link.next().remove(3)
        println(link)
        println(removed)
    }
}