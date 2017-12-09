package com.nibado.projects.advent.y2016

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceString

object Day09 : Day {
    val input = resourceString(2016, 9)

    override fun part1() = decompress(input).length.toString()
    override fun part2() = count(input).toString()

    private fun decompress(line: String) : String {
        val builder = StringBuilder(line.length * 8)
        var index = 0

        while(index <= line.length) {
            val nextCompress = line.indexOf('(', index)
            val nextCompressEnd = line.indexOf(')', nextCompress)

            if(nextCompress < 0) {
                builder.append(line.substring(index))
                break
            } else {
                builder.append(line.subSequence(index, nextCompress))
            }

            val repeat = line.subSequence(nextCompress + 1, nextCompressEnd).split("x").map { it.toInt() }

            index = nextCompressEnd + 1 + repeat[0]

            for(i in 1 .. repeat[1]) {
                builder.append(line.subSequence(nextCompressEnd + 1, nextCompressEnd + 1 + repeat[0]))
            }
        }

        return builder.toString()
    }

    fun count(line: CharSequence) : Long {
        var index = 0
        var count = 0L

        while(index <= line.length) {
            val nextCompress = line.indexOf('(', index)
            val nextCompressEnd = line.indexOf(')', nextCompress)

            if(nextCompress < 0) {
                count += line.length - index
                break
            } else {
                count += nextCompress - index
            }

            val repeat = line.subSequence(nextCompress + 1, nextCompressEnd).split("x").map { it.toInt() }
            index = nextCompressEnd + 1 + repeat[0]

            (1 .. repeat[1]).forEach {
                count += count(line.subSequence(nextCompressEnd + 1, nextCompressEnd + 1 + repeat[0]))
            }
        }

        return count
    }
}