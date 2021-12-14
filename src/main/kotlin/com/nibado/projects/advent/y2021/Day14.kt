package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.*

object Day14 : Day {
    private val values = resourceStrings(2021, 14).let { (template, rules) -> template.toList() to
            rules.split("\n").map { it.split(" -> ").let { (from, to) -> from.toList() to to.first()} }.toMap() }

    private val memo = mutableMapOf<Pair<List<Char>, Int>, Map<Char, Long>>()

    private fun run(input: List<Char>) : List<Char> {
        return input.windowed(2, 1).map {
            listOf(it.first(), values.second[it]!!)
        }.flatten() + input.last()
    }


    override fun part1() : Int {
        var template = values.first
        repeat(10) {
            template = run(template)
        }

        val counts = template.groupBy { it }.map { (k, v) -> k to v.size }.toMap()

        return counts.maxOf { (_, v) -> v } - counts.minOf { (_, v) -> v }
    }

    private fun counts(chars: List<Char>, depth: Int) : Map<Char, Long> {
        if(memo.containsKey(chars to depth)) {
            return memo[chars to depth]!!
        }
        val charsInserted = listOf(chars.first(), values.second[chars]!!, chars.last())
        val result = when (depth) {
            0 -> merge(mapOf(chars[0] to 1L), mapOf(chars[1] to 1L))
            1 -> merge(mapOf(charsInserted[0] to 1L), mapOf(charsInserted[1] to 1L), mapOf(charsInserted[2] to 1L))
            else -> {
                val charPairs = charsInserted.windowed(2, 1)
                merge(counts(charPairs.first(), depth - 1), counts(charPairs.last(), depth - 1).without(charPairs.last().first()))
            }
        }
        memo[chars to depth] = result
        return result
    }

    private fun <K> merge(vararg maps: Map<K, Long>) : Map<K, Long> =
        (maps.flatMap { it.keys }.toSet()).associateWith { k -> maps.sumOf { it.getOrDefault(k, 0) } }

    private fun <K> Map<K, Long>.without(k: K) = this.entries.filterNot { it.key == k }.map { (k, v) -> k to v }.toMap()

    override fun part2() : Long {
        val counts = values.first.windowed(2, 2).map { counts(it, 1) }
            .let { merge(merge(*it.toTypedArray()), mapOf(values.first.last() to 1L)) }

        matchCounts(counts, "NBCCNBBBCBHCB")

        return counts.maxOf { (_, v) -> v } - counts.minOf { (_, v) -> v }
    }

    private fun matchCounts(counts: Map<Char, Long>, input: String) {
        val actual = input.associateWith { k -> input.count { it == k }.toLong() }
        //val match = actual.all { (k, v) -> counts[k] == v }
        println("$counts: ${counts == actual} ${actual}")
    }
}

fun main() {
    println(Day14.part1())
}