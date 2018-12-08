package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceString

object Day08 : Day {
    private val tree = resourceString(2018, 8).split(" ").map(String::toInt).iterator().let(::toTree)

    override fun part1() = tree.all().sumBy { it.metaData.sum() }
    override fun part2() = tree.walk()

    private fun toTree(queue: Iterator<Int>): Node {
        val childAmount = queue.next()
        val metaAmount = queue.next()

        val children = (1 .. childAmount).map { toTree(queue) }
        val metaData = (1 .. metaAmount).map { queue.next() }

        return Node(children, metaData)
    }

    data class Node(val children: List<Node>, val metaData: List<Int>) {
        fun all(): List<Node> = children.flatMap { it.all() } + this
        fun walk(): Int = if (children.isEmpty()) {
            metaData.sum()
        } else {
            metaData.map { it - 1 }
                    .filterNot { it < 0 || it >= children.size }
                    .map { children[it].walk() }
                    .sum()
        }
    }
}