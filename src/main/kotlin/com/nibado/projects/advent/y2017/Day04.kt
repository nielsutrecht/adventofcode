package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines
import kotlin.streams.toList

object Day04 : Day {
    override fun main(args: Array<String>) {
        val lines = resourceLines(4).map {
            it.split(" ").toList() }.toList()

            part1(lines)
            part2(lines)
    }

    fun part1(lines: List<List<String>>) {
        println(lines.filter { it.size == HashSet<String>(it).size }.count())
    }

    fun part2(lines: List<List<String>>) {
        println(lines.map { it.map { it.toList().sorted().toString() } }.filter { it.size == HashSet<String>(it).size }.count())
    }
}
