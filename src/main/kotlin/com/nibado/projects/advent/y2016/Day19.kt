package com.nibado.projects.advent.y2016

import com.nibado.projects.advent.Day
import java.util.*

object Day19 : Day {
    private val input = 3012210

    override fun part1(): String {
        val queue = ArrayDeque<Int>((1..input).toList())
        while (queue.size > 1) {
            queue.add(queue.pop())
            queue.pop()
        }
        return queue.first().toString()
    }

    override fun part2(): String {
        val left = ArrayDeque<Int>((1..input / 2).toList())
        val right = ArrayDeque<Int>(((input / 2) + 1..input).toList())

        while (left.size + right.size > 1) {
            if (left.size > right.size) left.pollLast()
            else right.pollFirst()

            right.addLast(left.pollFirst())
            left.addLast(right.pollFirst())
        }

        return (left.firstOrNull() ?: right.first()).toString()
    }
}
