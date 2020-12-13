package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*
import kotlin.math.abs

object Day10 : Day {
    private val adapters = resourceLines(2020, 11).map { it.toInt() }.sorted()

    override fun part1() : Int {
        val chain = listOf(0) + adapters + (adapters.last() + 3)
        val map = chain.windowed(2).map { (a, b) -> b - a }.groupBy { it }.map { (k, v) -> k to v.size }
                .toMap()
        return map[1]!! * map[3]!!
    }

    override fun part2() : Long {
        val chain = listOf(0) + adapters + (adapters.last() + 3)
        var count = 1L

        var start = 0
        var end = start

        println(chain)

        while(true) {
            while(chain[end] - chain[start] <= 3)
                end++

            //println(chain.subList(start, end))

            when(end - start) {
                3 -> count *= 2
                4 -> count *= 4
            }

            start++

            if(start >= chain.size - 2) {
                break
            }
        }


        return count
    }

    fun countChains(current: MutableList<Int>) : Long {
        var count = 1L
        var found = true
        val removed = mutableListOf<Int>()
        while(found) {
            found = false
            println(current)
            for(i in 1 .. current.size - 2) {
                if(abs(current[i+1] - current[i - 1]) in 1 .. 3) {
                    found = true
                    current.removeAt(i)
                    removed += i
                    count++
                    break
                }
            }
        }
        println(removed)
        return count
    }

}

fun main() {
    //println(Day10.part1())
    //println(Day10.part2())

    println(resourceLines(2020, 12).map { it.toLong() }.reduce { a, b -> a * b })
}
