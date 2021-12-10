package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.*

object Day10 : Day {
    private val values = resourceLines(2021, 10).map(::parseLine)

    private fun parseLine(line: String) : Pair<Int, Long> {
        val stack = ArrayDeque<Char>()

        for(c in line) {
            when(c) {
                ')' -> if(stack.firstOrNull() == '(') stack.removeFirst() else return 3 to 0
                ']' -> if(stack.firstOrNull() == '[') stack.removeFirst() else return 57 to 0
                '}' -> if(stack.firstOrNull() == '{') stack.removeFirst() else return 1197 to 0
                '>' -> if(stack.firstOrNull() == '<') stack.removeFirst() else return 25137 to 0
                else -> stack.addFirst(c)
            }
        }

        val score = stack.map { when(it) {
            '(' -> 1L
            '[' -> 2L
            '{' -> 3L
            else -> 4L
        } }.fold(0L) { acc, e -> acc * 5 + e }

        return 0 to score
    }

    override fun part1() = values.sumOf { it.first }
    override fun part2() = values.filter { it.first == 0 }.map { it.second }.sorted().let { it[it.size / 2] }
}