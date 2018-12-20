package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Direction
import com.nibado.projects.advent.Point
import com.nibado.projects.advent.resourceString

object Day20 : Day {
    private val input = resourceString(2018, 21).let { it.substring(1, it.length - 1) }
    private val directions = Direction.values().map { it.toString()[0] to it }.toMap()

    data class Tree(val steps: CharSequence, val branches: List<Tree>) {
        override fun toString() = StringBuilder().let { toString(it); it.toString() }

        private fun toString(builder: StringBuilder, depth: Int = 0) {
            val pre = "  ".repeat(depth)

            builder.append(pre).append(if (steps.isBlank()) "*" else steps).append('\n')
            branches.forEach { it.toString(builder, depth + 1) }
        }

        fun walk(p: Point = Point(0, 0), set: MutableSet<Pair<Point, Point>>): Set<Pair<Point, Point>> {
            var cur = p

            for(s in steps) {
                val new = cur + directions[s]!!

                if(set.contains(new to cur)) {
                    set += cur to new
                }
            }

            return set
        }
    }

    fun buildTree(s: CharSequence): Tree {
        val splits = findSplits(s).map { s.subSequence(it.first, it.second) }

        if (splits.size == 1) {
            return Tree(splits.first(), listOf())
        } else {
            return Tree("", splits.map { buildTree(it) })
        }
    }

    private fun findSplits(s: CharSequence): List<Pair<Int, Int>> {
        val splits = mutableListOf(0)

        var depth = 0

        for (i in s.indices) {
            if (s[i] == ')') {
                depth--
                if (depth == 0) {
                    splits += i
                    splits += i + 1
                }
            } else if (s[i] == '(') {
                if (depth == 0) {
                    splits += i
                    splits += i + 1
                }
                depth++
            } else if (s[i] == '|' && depth == 0) {
                splits += i
                splits += i + 1
            }
        }

        splits += s.length

        return splits.windowed(2, 2).map { (a, b) -> a to b }
    }

    override fun part1(): Int {
        /*
        *
            ENNWSWW
                NEWS
                *
            SSSEEN
                WNSE
                *
            EE
                SWEN
                *
            NNN
         */
        val list = listOf("ENWWW(NEEE|SSE(EE|N))", "ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN")

        val inp = list[1]

        println(inp)
        val tree = buildTree(inp)

        println(tree)
        return 0
    }

    override fun part2(): Int {
        return 0
    }
}

fun main(args: Array<String>) {
    println(Day20.part1())
}