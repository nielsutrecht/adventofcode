package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*

object Day22 : Day {
    private val players = resourceStrings(2020, 22).let { (a, b) ->
        a.split("\n").drop(1).map { it.toInt() } to
                b.split("\n").drop(1).map { it.toInt() }
    }

    override fun part1(): Int {
        val (p1, p2) = players.let { (a,b) -> a.toMutableList() to b.toMutableList() }

        while(p1.isNotEmpty() && p2.isNotEmpty()) {
            val num1 = p1.removeAt(0)
            val num2 = p2.removeAt(0)

            if(num1 > num2) p1.addAll(listOf(num1, num2))
            else if(num2 > num1) p2.addAll(listOf(num2, num1))
        }

        return (if(p1.isNotEmpty()) p1 else p2).score()
    }

    override fun part2() = combat(players.first, players.second).let { (_, deck) -> deck.score() }

    private fun combat(one: List<Int>, two: List<Int>) : Pair<Int, Collection<Int>> {
        val (p1, p2) = (one to two).let { (a,b) -> a.toMutableList() to b.toMutableList() }

        val p1Decks = mutableSetOf<List<Int>>()
        val p2Decks = mutableSetOf<List<Int>>()

        while(p1.isNotEmpty() && p2.isNotEmpty()) {
            p1Decks += p1.toList()
            p2Decks += p2.toList()

            val num1 = p1.removeAt(0)
            val num2 = p2.removeAt(0)

            if(p1Decks.contains(p1.toList()) || p2Decks.contains(p2.toList())) {
                return 1 to emptyList()
            }

            val winner = if(num1 <= p1.size && num2 <= p2.size) {
                combat(p1.toList().take(num1), p2.toList().take(num2)).first
            } else {
                when {
                    num1 > num2 -> 1
                    num2 > num1 -> 2
                    else -> 0
                }
            }

            when(winner) {
                1 -> p1.addAll(listOf(num1, num2))
                2 -> p2.addAll(listOf(num2, num1))
            }
        }

        return if(p1.isEmpty()) {
            2 to p2.toList()
        } else {
            1 to p1.toList()
        }
    }

    private fun Collection<Int>.score() = reversed().mapIndexed { i, v -> (i + 1) to v }
            .fold(0) { acc, (a,b) -> acc + a * b}
}