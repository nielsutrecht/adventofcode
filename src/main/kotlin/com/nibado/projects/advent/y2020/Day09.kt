package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*

object Day09 : Day {
    private val numbers = resourceLines(2020, 9).map { it.toLong() }
    private const val preAmbleSize = 25

    private val part1Number : Long by lazy { numbers.asSequence().drop(preAmbleSize).mapIndexedNotNull { index, i ->
        if(pairs(numbers.subList(index, index + preAmbleSize)).map { (a, b) -> a + b }.toSet().contains(i)) {
            null
        } else i
    }.first() }

    override fun part1() = part1Number
    override fun part2() : Long {
        val ranges = mutableListOf<Pair<Int, Int>>()
        for(i in numbers.indices) {
            var sum = 0L
            for(j in i .. numbers.indices.last) {
                sum += numbers[j]
                if(sum == part1Number) {
                    ranges += i to j
                    break
                }
            }
        }

        return ranges.maxByOrNull { (a,b) -> b - a }!!.let { (a, b) -> numbers.subList(a, b).minOrNull()!! + numbers.subList(a,b).maxOrNull()!! }
    }

    private fun pairs(list: List<Long>): List<Pair<Long, Long>> = list.indices
            .flatMap { a -> list.indices.map { b -> a to b } }
            .filterNot { (a, b) -> a == b }
            .map { (a, b) -> list[a] to list[b] }

}
