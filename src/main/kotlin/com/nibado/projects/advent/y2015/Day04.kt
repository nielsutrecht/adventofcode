package com.nibado.projects.advent.y2015

import com.nibado.projects.advent.*
import java.security.MessageDigest

object Day04 : Day {
    private val input = resourceString(2015, 4).trim().toByteArray()

    private fun find(zeroes: Int) =
        md5().first { (a, _) ->
            var ok = true
            for(i in 0 until zeroes) {
                val mask = if (i % 2 == 0) 0xF0 else 0x0F
                if(a[i / 2].toInt() and mask != 0) {
                    ok = false
                    break
                }
            }
            ok
        }

    private fun md5() = sequence<Pair<ByteArray, Int>> {
        val md5 = MessageDigest.getInstance("md5")

        for(i in 0 .. Int.MAX_VALUE) {
            md5.update(input)
            yield(md5.digest(i.toString().toByteArray()) to i)
        }
    }

    override fun part1() = find(5)
    override fun part2() = find(6)
}
