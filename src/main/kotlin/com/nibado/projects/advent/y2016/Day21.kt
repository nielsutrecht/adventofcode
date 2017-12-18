package com.nibado.projects.advent.y2016

import com.nibado.projects.advent.*

object Day21 : Day {
    val matchers = mapOf(
            "rotr" to Regex("rotate right ([0-9]+) steps?"),
            "rotl" to Regex("rotate left ([0-9]+) steps?"),
            "swapl" to Regex("swap letter ([a-z]) with letter ([a-z])"),
            "movep" to Regex("move position ([0-9]+) to position ([0-9]+)"),
            "swapp" to Regex("swap position ([0-9]+) with position ([0-9]+)"),
            "rotp" to Regex("rotate based on position of letter ([a-z])"),
            "reverse" to Regex("reverse positions ([0-9]+) through ([0-9]+)"))

    val input = resourceRegex(2016, 22, matchers)

    override fun part1() : String {
        var list = "abcde".toCharArray().toList()
        val commands = input.forEach {
            when(it.first) {
                "rotr" -> list = rotateRight(list, it.second[1].toInt())
                "rotl" -> list = rotateLeft(list, it.second[1].toInt())
                "swapp" -> list = swap(list, it.second[1].toInt(), it.second[2].toInt())
                "swapl" -> list = swapByValue(list, it.second[1][0], it.second[2][0])
                //"reverse" -> reverse(list, it.second[1].toInt(), it.second[2].toInt() - it.second[1].toInt())
                else -> {}
            }

            println(list.joinToString(""))
        }

        return ""
    }
    override fun part2() = ""



    /*
    swap position X with position Y means that the letters at indexes X and Y (counting from 0) should be swapped.
swap letter X with letter Y means that the letters X and Y should be swapped (regardless of where they appear in the string).
rotate left/right X steps means that the whole string should be rotated; for example, one right rotation would turn abcd into dabc.
rotate based on position of letter X means that the whole string should be rotated to the right based on the index of letter X (counting from 0) as determined before this instruction does any rotations. Once the index is determined, rotate the string to the right one time, plus a number of times equal to that index, plus one additional time if the index was at least 4.
reverse positions X through Y means that the span of letters at indexes X through Y (including the letters at X and Y) should be reversed in order.
move position X to position Y means that the letter which is at index X should be removed from the string, then inserted such that it ends up at index Y.
     */
}

fun main(args: Array<String>) {
    Day21.part1()
}