package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceString
import java.util.*

object Day08 : Day {
    private val tree = resourceString(2018, 8).split(" ").map { it.toInt() }.let { LinkedList<Int>(it) }.let(::toTree)

    override fun part1() = tree.all().sumBy { it.metaData.sum() }
    override fun part2() = tree.walk()

    private fun toTree(queue: Deque<Int>) : Node {
        val childAmount = queue.removeFirst()
        val metaAmount = queue.removeFirst()

        val children = (0 until childAmount).map { toTree(queue) }
        val metaData = (0  until metaAmount).map { queue.removeFirst() }

        return Node(children, metaData)
    }

    data class Node(val children: List<Node>, val metaData: List<Int>) {
        fun all() : List<Node> = children.flatMap { it.all() } + this
        fun walk() : Int = if(children.isEmpty()) {
            metaData.sum()
        } else {
            metaData.map { it - 1 }
                    .filterNot { it < 0 || it >= children.size }
                    .map { children[it].walk() }
                    .sum()
        }
    }
}