package com.nibado.projects.advent.y2016

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Hash

object Day05 : Day {
    const val input = "abbhdwsy"

    private val hashes: List<String> by lazy { hashes(8) }

    private fun hashes(amount: Int) : List<String> {
        val list: MutableList<String> = mutableListOf()
        var counter = 0
        val positions = mutableSetOf<Char>()
        while(positions.size < amount) {
            val hash = Hash.md5(input + counter)

            if(hash.startsWith("00000")) {
                list += hash

                if(hash[5] in '0'..'7') {
                    positions += hash[5]
                }
            }

            counter++
        }

        return list
    }

    override fun part1() = hashes.subList(0, 8).map { it[5] }.joinToString ("")
    override fun part2() : String {
        val password = StringBuilder("        ")
        hashes.map { Pair(it[5] - '0', it[6]) }.filter { it.first in 0 .. 7 }.forEach {
            if(password[it.first] == ' ') {
                password[it.first] = it.second
            }
        }
        return password.toString()
    }
}



