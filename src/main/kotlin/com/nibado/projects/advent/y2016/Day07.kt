package com.nibado.projects.advent.y2016

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines
import com.nibado.projects.advent.slices

object Day07 : Day {
    private val input = resourceLines(2016, 7).map(Day07::parse)

    override fun part1() = input.count { it.tls() }.toString()
    override fun part2() = input.count { it.ssl() }.toString()

    private fun parse(line: String) : IpAddr {
        val add = mutableListOf<String>()
        val hyper = mutableListOf<String>()

        var s = ""
        var inHyper = false

        line.forEach {
            if(it in 'a'..'z') {
                s += it
            } else {
                (if(inHyper) hyper else add) += s
                s = ""
                inHyper = it == '['
            }
        }

        (if(inHyper) hyper else add) += s

        return IpAddr(add, hyper)
    }

    private fun abba(line: String) = slices(line, 4)
            .any { it[0] == it[3] && it[1] == it[2] && it[0] != it[1] }

    private fun aba(line: String) = slices(line, 3)
            .filter { it[0] == it[2] && it[0] != it[1]}

    private fun bab(line: String) = "" + line[1] + line[0] + line[1]

    private data class IpAddr(val address: List<String>, val hyper: List<String>) {
        fun tls() = address.any { abba(it) } && hyper.none { abba(it) }
        fun ssl() = address.flatMap { aba(it) }.filter { hyper.flatMap { aba(it).map { bab(it) } }.toSet().contains(it) }.any()
    }
}