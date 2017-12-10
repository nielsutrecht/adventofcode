package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.reverse
import com.nibado.projects.advent.toHex
import com.nibado.projects.advent.xor

object Day10 : Day {
    private val input = "189,1,111,246,254,2,0,120,215,93,255,50,84,15,94,62"
    private val lengths = input.split(",").map { it.toInt() }.toList()
    private val chars = input.toCharArray().map { it.toInt() }.toList() + listOf(17, 31, 73, 47, 23)

    override fun part1() : String {
        val list = (0 .. 255).toMutableList()
        var current = 0
        var skipSize = 0

        lengths.forEach {
            reverse(list, current, it)
            current += it + skipSize
            skipSize++
        }

        return (list[0] * list[1]).toString()
    }

    override fun part2() : String {
        val list = (0 .. 255).toMutableList()
        var current = 0
        var skipSize = 0

        (0 .. 63).forEach {
            chars.forEach {
                reverse(list, current, it)
                current += it + skipSize
                skipSize++
            }
        }

        return (0 .. 15).map { list.subList(it * 16, it * 16 + 16) }.map { xor(it) }.toHex()
    }
}