package com.nibado.projects.advent.y2016

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Hash

object Day14 : Day {
    private val input = "qzyelonm"
    private val hashes = mutableListOf<String>()
    private val stretched = mutableListOf<String>()

    override fun part1() = solve(Day14::get).toString()
    override fun part2() = solve(Day14::getS).toString()

    private fun solve(getter: (Int) -> String) : Int {
        var count = 0
        var i = 0
        while(count < 64) {
            i++
            val c = three(getter(i))

            if(c != null) {
                if(five(i, c, getter)) {
                    count++
                }
            }
        }
        return i
    }

    private fun get(num: Int) : String {
        for(i in hashes.size .. num) {
            hashes += Hash.md5(input + i)
        }

        return hashes[num]
    }

    private fun getS(num: Int) : String {
        for(i in stretched.size .. num) {
            stretched += (1 .. 2016).fold(get(num), {a, b -> Hash.md5(a)})
        }

        return stretched[num]
    }

    private fun three(input: String) : Char? {
        return (0 .. input.length - 3)
                .firstOrNull { input[it] == input[it + 1] && input[it] == input[it + 2] }
                ?.let { input[it] }
    }

    private fun five(index: Int, c: Char, getter: (Int) -> String) : Boolean {
        val needle = c.toString().repeat(5)
        return (index + 1 .. index + 1000).any { getter(it).contains(needle) }
    }
}