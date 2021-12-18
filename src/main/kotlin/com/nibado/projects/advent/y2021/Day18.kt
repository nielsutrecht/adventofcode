package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.*

object Day18 : Day {
    private val input = resourceLines(2021, 18)

    private fun String.toSnailNumber() = if(length == 1) toSnailNumberLit() else toSnailNumberPair()
    private fun String.toSnailNumberLit() = SnailNumberLit(toInt())
    private fun String.toSnailNumberPair(): SnailNumber {
        val inside = drop(1).dropLast(1)
        var left = ""
        var brackets = 0
        for (c in inside) {
            if (c == '[') brackets++
            if (c == ']') brackets--
            if (c == ',' && brackets == 0) break
            left += c
        }
        val right = inside.drop(left.length + 1)
        return SnailNumberPair(left.toSnailNumber(), right.toSnailNumber())
    }

    private abstract class SnailNumber {
        abstract fun magnitude(): Int
        abstract fun addToLeftMost(value: Int)
        abstract fun addToRightMost(value: Int)
        abstract fun split(): Boolean
        abstract fun explode(depth: Int = 0): Pair<Int, Int>?

        fun reduce() {
            do {
                val exploded = explode() != null
                val split = if (!exploded) split() else false
            } while (exploded || split)
        }
    }

    private class SnailNumberLit(var value: Int) : SnailNumber() {
        override fun magnitude() = value
        override fun addToLeftMost(value: Int) = run { this.value += value }
        override fun addToRightMost(value: Int) = run { this.value += value }
        override fun split() = false
        override fun explode(depth: Int): Pair<Int, Int>? = null
        override fun toString() = value.toString()
    }

    private class SnailNumberPair(var left: SnailNumber, var right: SnailNumber) : SnailNumber() {
        override fun magnitude() = 3 * left.magnitude() + 2 * right.magnitude()
        override fun addToLeftMost(value: Int) = left.addToLeftMost(value)
        override fun addToRightMost(value: Int) = right.addToRightMost(value)

        override fun split(): Boolean {
            if (left is SnailNumberLit) {
                (left as SnailNumberLit).value.let {
                    if (it >= 10) {
                        left = SnailNumberPair(SnailNumberLit(it / 2), SnailNumberLit((it + 1) / 2))
                        return true
                    }
                }
            }
            if (left.split()) {
                return true
            }
            if (right is SnailNumberLit) {
                (right as SnailNumberLit).value.let {
                    if (it >= 10) {
                        right = SnailNumberPair(SnailNumberLit(it / 2), SnailNumberLit((it + 1) / 2))
                        return true
                    }
                }
            }
            return right.split()
        }

        override fun explode(depth: Int): Pair<Int, Int>? {
            if (depth == 4) {
                return (left as SnailNumberLit).value to (right as SnailNumberLit).value
            }
            left.explode(depth + 1)?.let { (first, second) ->
                if (first != -1 && second != -1) {
                    this.left = SnailNumberLit(0)
                    this.right.addToLeftMost(second)
                    return first to -1
                }
                if (second != -1) {
                    this.right.addToLeftMost(second)
                    return -1 to -1
                }
                return first to -1
            }
            right.explode(depth + 1)?.let { (first, second) ->
                if (first != -1 && second != -1) {
                    this.right = SnailNumberLit(0)
                    this.left.addToRightMost(first)
                    return -1 to second
                }
                if (first != -1) {
                    this.left.addToRightMost(first)
                    return -1 to -1
                }
                return -1 to second
            }
            return null
        }

        override fun toString() = "[$left,$right]"
    }

    override fun part1(): Int {
        return input.reduce { acc, s -> "[$acc,$s]".toSnailNumber().apply { reduce() }.toString() }
            .let { it.toSnailNumber().magnitude() }
    }

    override fun part2(): Int {
        return input.maxOf { first ->
            input.filterNot { it == first }.maxOf { second ->
                "[$first,$second]".toSnailNumber().apply { reduce() }.magnitude()
            }
        }
    }
}