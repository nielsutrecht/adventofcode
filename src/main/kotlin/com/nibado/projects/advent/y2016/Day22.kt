package com.nibado.projects.advent.y2016

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Point
import com.nibado.projects.advent.resourceLines

object Day22 : Day {
//    Filesystem              Size  Used  Avail  Use%
//    /dev/grid/node-x0-y0     92T   68T    24T   73%
    val regex = Regex("/dev/grid/node-x([0-9]+)-y([0-9]+)[ ]+([0-9]+)T[ ]+([0-9]+)T[ ]+([0-9]+)T[ ]+([0-9]+)%")
    val input = resourceLines(2016, 22).filter { it.startsWith("/dev") }
            .map { regex.matchEntire(it)!!.groupValues.drop(1).map { it.toInt() } }
            .map { Node(Point(it[0], it[1]), it[3], it[4]) }

    override fun part1(): String {
        val pairs = mutableSetOf<Pair<Node, Node>>()
        input
                .filter { it.used != 0 }
                .forEach { a ->
                    input
                            .filter { a != it && !pairs.contains(a to it) && !pairs.contains(it to a) && a.used <= it.available }
                            .forEach { pairs.add(a to it) }
                }
        return pairs.size.toString()
    }
    override fun part2() = ""

    data class Node(val p: Point, val used: Int, val available: Int)
}

fun main(args: Array<String>) {
    println(Day22.part1())
}