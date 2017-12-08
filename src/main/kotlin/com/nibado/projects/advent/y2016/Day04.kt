package com.nibado.projects.advent.y2016

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceRegex

object Day04 : Day {
    val comp : Comparator<Pair<Char, Int>> = compareByDescending<Pair<Char, Int>>{ it.second }.thenBy { it.first }
    private val regex = Regex("([a-z-]+)-([0-9]+)\\[([a-z]+)]")
    val input = resourceRegex(2016, 4, regex).map { Room(it[1], it[2].toInt(), it[3].toCharArray().toHashSet()) }

    override fun part1() = input.filter { it.real() }.sumBy { it.sector }.toString()
    override fun part2() = input.find { it.name() == "northpole object storage" }!!.sector.toString()
}

data class Room(val name: String, val sector: Int, val checksum: Set<Char>) {
    fun counts() =  name.toCharArray()
            .filter { it != '-' }
            .groupBy { it }
            .map { Pair(it.key, it.value.size) }
            .sortedWith(Day04.comp)

    fun real() =
        counts().subList(0, 5).map { it.first }.count { !checksum.contains(it) } == 0

    fun name() = name.toCharArray().map { if(it == '-') ' ' else 'a' + (it - 'a' + sector % 26) % 26 }.joinToString("")
}

