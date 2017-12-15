package com.nibado.projects.advent.y2016

import com.nibado.projects.advent.Day

object Day16 : Day {
    val input = "10011111011011001".toCharArray().map { if(it == '1') 1 else 0 }.toList()

    override fun part1() = solve(272)
    override fun part2() = solve(35651584)

    private fun solve(diskSize: Int) : String {
        var list = input

        while(list.size < diskSize) {
            list = list + listOf(0) + invert(list)
        }

        return checksum(list.take(diskSize)).joinToString("")
    }

    private fun invert(s: List<Int>) = s.reversed().map { if(it == 1) 0 else 1 }
    private fun checksum(s: List<Int>) : List<Int> {
        var list = s
        do {
            list = (0 until list.size step 2).map { if(list[it] == list[it + 1]) 1 else 0 }
        } while(list.size % 2 == 0)

        return list
    }
}