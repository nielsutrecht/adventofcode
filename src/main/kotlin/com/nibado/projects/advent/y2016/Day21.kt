package com.nibado.projects.advent.y2016

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.collect.*
import com.nibado.projects.advent.resourceRegex

object Day21 : Day {
    private val matchers = mapOf(
            "rotr" to Regex("rotate right ([0-9]+) steps?"),
            "rotl" to Regex("rotate left ([0-9]+) steps?"),
            "swapl" to Regex("swap letter ([a-z]) with letter ([a-z])"),
            "movep" to Regex("move position ([0-9]+) to position ([0-9]+)"),
            "swapp" to Regex("swap position ([0-9]+) with position ([0-9]+)"),
            "rotp" to Regex("rotate based on position of letter ([a-z])"),
            "reverse" to Regex("reverse positions ([0-9]+) through ([0-9]+)"))

    val input = resourceRegex(2016, 21, matchers)

    override fun part1() = scramble("abcdefgh")
    override fun part2() = "abcdefgh".toCharArray().toList().permutations().map { it.joinToString("") }.find { scramble(it) == "fbgdceah" }!!

    private fun scramble(password: String) : String {
        var list = password.toCharArray().toList()
        input.forEach {
            when(it.first) {
                "rotr" -> list = list.rotateRight(it.second[1].toInt())
                "rotl" -> list =  list.rotateLeft(it.second[1].toInt())
                "swapp" -> list = list.swap(it.second[1].toInt(), it.second[2].toInt())
                "swapl" -> list = list.swapByValue(it.second[1][0], it.second[2][0])
                "reverse" -> list = list.reverse(it.second[1].toInt(), it.second[2].toInt() - it.second[1].toInt() + 1  )
                "movep" -> list = list.move(it.second[1].toInt(), it.second[2].toInt())
                "rotp" -> {
                    val pos = list.indexOf(it.second[1][0])
                    list = list.rotateRight(if(pos >= 4) pos + 2 else pos + 1)
                }
                else -> {}
            }
        }

        return list.joinToString("")
    }
}