package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceString
import java.util.*

object Day09 : Day {
    val input: Pair<String, Int> by lazy { Day09.clean((resourceString(9))) }

    fun clean(input: String) : Pair<String, Int> {
        val builder = StringBuilder(input.length)
        var inGarbage = false
        var skip = false
        var count = 0
        for(c in input) {
            if(skip) {
                skip = false
                continue
            }
            if(c == '!') {
                skip = true
                //count++
                continue
            } else if(c == '<') {
                if(inGarbage) {
                    count++
                }
                inGarbage = true
                continue
            } else if(c == '>') {
                inGarbage = false
                continue
            } else {
                if(!inGarbage) {
                    builder.append(c)
                } else {
                    count++
                }
            }
        }

        return Pair(builder.toString(), count)
    }

    fun score(input: String) : Int {
        val chars = LinkedList<Char>(input.toCharArray().toList())
        return score(chars, 0)
    }

    fun score(input: Queue<Char>, depth: Int) : Int {
        var score = depth
        while(input.isNotEmpty()) {
            val c = input.remove()
            if(c == '{') {
                score += score(input, depth + 1)
            } else if (c == '}') {
                return score
            }
        }

        return score
    }

    override fun part1() = score(input.first).toString()
    override fun part2() = input.second.toString()
}
