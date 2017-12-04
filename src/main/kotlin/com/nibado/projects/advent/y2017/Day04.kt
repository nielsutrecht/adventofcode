package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines
import kotlin.streams.toList

object Day04 : Day {
    override fun main(args: Array<String>) {
        val lines = resourceLines(4).map {
            it.split(" ").toList()
        }.toList()

        println(part1(lines))
        println(part2(lines))
    }

    fun part1(lines: List<List<String>>) =
            lines.filter { it.size == it.toSet().size }.count()

    fun part2(lines: List<List<String>>) =
            lines.map { it.map { it.toList().sorted().toString() } }.filter { it.size == it.toSet().size }.count()
}