package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.reverse
import com.nibado.projects.advent.toHex
import com.nibado.projects.advent.xor

object Day10 : Day {
    private val input = "189,1,111,246,254,2,0,120,215,93,255,50,84,15,94,62"
    private val lengths = input.split(",").map { it.toInt() }.toList()
    private val chars = input.toCharArray().map { it.toInt() }.toList() + listOf(17, 31, 73, 47, 23)

    override fun part1() = knot(lengths).subList(0, 2).fold(1, {a, b -> a * b}).toString()
    override fun part2() = (0 .. 15).map { knot(chars, 64).subList(it * 16, it * 16 + 16) }.map { xor(it) }.toHex()

    fun knot(lengths: List<Int>, iterations: Int = 1) : List<Int> {
        val list = (0 .. 255).toMutableList()
        var current = 0
        var skipSize = 0

        (0 until iterations).forEach {
            lengths.forEach {
                reverse(list, current, it)
                current += it + skipSize
                skipSize++
            }
        }

        return list
    }
}