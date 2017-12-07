package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines
import kotlin.streams.toList

object Day02 : Day {
    val lines = resourceLines(2).map {
        it.split("\t").stream().map { it.toInt() }.toList() }.toList()

    override fun part1() = lines.map{ Math.abs(it.min()!! - it.max()!!) }.sum().toString()

    override fun part2() = lines.map(Day02::part2map).sum().toString()

    fun part2map(line: List<Int>) : Int {
        for(a in line) {
            line
                    .filter { a != it && a % it == 0 }
                    .forEach { return a / it }
        }

        throw RuntimeException()
    }
}