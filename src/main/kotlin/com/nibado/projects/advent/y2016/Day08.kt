package com.nibado.projects.advent.y2016

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines
import java.util.*

object Day08 : Day {
    private val input = resourceLines(2016, 8)

    override fun part1() = ""
    override fun part2() = ""

    object Screen {
        val width = 30
        val height = 6

        val pixels = BitSet(width * height)

        fun get(x: Int, y: Int) = pixels[index(x, y)]
        fun set(x: Int, y: Int, v: Boolean) {
            pixels[index(x, y)] = v
        }

        fun rect(a: Int, b: Int) {
            for(y in 0 until b) {
                for(x in 0 until a) {
                    set(x, y, true)
                }
            }
        }

        fun rotCol(x: Int, amount: Int) {

        }

        fun rotRow(y: Int, amount: Int) {

        }

        fun index(x: Int, y: Int) = x + y * width

    }
}