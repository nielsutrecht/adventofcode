
package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.collect.Link

object Day23 : Day {
    private val input = "589174263".map { it - '0' }

    override fun part1() : Int {
        val game = runGame(input, 100)

        return game.linkSequence().first { it.value == 1 }.drop(1)
                .joinToString("") { it.toString() }.toInt()
    }

    override fun part2() : Long {
        val game = runGame(input + (10 .. 1_000_000), 10_000_000)
        val one = game.linkSequence().first { it.value == 1 }

        return one.next().value.toLong() * one.next().next().value.toLong()
    }

    private fun runGame(input: List<Int>, repeats: Int) : Link<Int> {
        var selected = Link.of(input)
        selected.lastLink().next = selected
        val index = selected.linkSequence().map { it.value to it }.toMap()
        val max = index.keys.max()!!
        repeat(repeats) { move ->
            val removed = selected.remove(3)

            val next = selected.next()

            var dest = selected.value
            do {
                dest--
                if(dest < 1) dest = max
            } while(dest in removed)

            index[dest]!!.addNext(removed)

            selected = next
        }

        return selected
    }
}
    