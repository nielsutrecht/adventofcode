package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines
import kotlin.streams.toList

object Day02 : Day {
    override fun main(args: Array<String>) {
        val lines = resourceLines(2).map {
            it.split("\t").stream().map { it.toInt() }.toList() }.toList()

        println(lines.map(Day02::delta).sum())
        println(lines.map(Day02::mod).sum())
    }

    fun mod(line: List<Int>) : Int {
        for(a in line) {
            line
                    .filter { a != it && a % it == 0 }
                    .forEach { return a / it }
        }

        throw RuntimeException()
    }

    fun delta(line: List<Int>) : Int {
        val min = line.min()!!
        val max = line.max()!!

        return Math.abs(min - max)
    }
}