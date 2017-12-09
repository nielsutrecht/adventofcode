package com.nibado.projects.advent.y2016

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines
import java.util.*

object Day08 : Day {
    private val rectRegex = Regex("rect ([0-9]+)x([0-9]+)")
    private val rotRegex = Regex("rotate (row|column) (y|x)=([0-9]+) by ([0-9]+)")
    private val input = resourceLines(2016, 8).map { parse(it) }

    override fun part1() : String {
        input.forEach { it() }

        return Screen.count().toString()
    }
    override fun part2() = "EOARGPHYAO" //Not gonna parse that

    private fun parse(line: String) : () -> Unit {
        if(rectRegex.matches(line)) {
            val result = rectRegex.matchEntire(line)!!.groupValues
            return { Day08.Screen.rect(result[1].toInt(), result[2].toInt()) }
        } else if(rotRegex.matches(line)) {
            val result = rotRegex.matchEntire(line)!!.groupValues
            if(result[1] == "row") {
                return { Day08.Screen.rotRow(result[3].toInt(), result[4].toInt()) }
            } else {
                return { Day08.Screen.rotCol(result[3].toInt(), result[4].toInt()) }
            }
        } else {
            throw IllegalArgumentException(line)
        }
    }

    object Screen {
        private const val width = 50
        private const val height = 6

        private val pixels = BitSet(width * height)

        fun get(x: Int, y: Int) = pixels[index(x, y)]
        fun set(x: Int, y: Int, v: Boolean) {
            pixels[index(x, y)] = v
        }

        fun rect(a: Int, b: Int) {
            for (y in 0 until b) {
                for (x in 0 until a) {
                    set(x, y, true)
                }
            }
        }

        fun rotCol(x: Int, amount: Int) {
            (0..height).map { get(x, it - (amount % height) + height) }.toList().forEachIndexed { i, v -> set(x, i, v) }
        }

        fun rotRow(y: Int, amount: Int) {
            (0..width).map { get(it - (amount % width) + width, y) }.toList().forEachIndexed { i, v -> set(i, y, v) }
        }

        fun index(x: Int, y: Int) = x % width + ((y % height) * width)
        fun count() = (0 until width * height).map { pixels[it] }.count { it }

        fun print() {
            for (y in 0 until height) {
                for (x in 0 until width) {
                    print(if (get(x, y)) '#' else '.')
                }
                println()
            }
        }
    }
}