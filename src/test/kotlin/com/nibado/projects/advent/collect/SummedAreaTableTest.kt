package com.nibado.projects.advent.collect

import com.nibado.projects.advent.Point
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SummedAreaTableTest {
    private val input = listOf(
        intArrayOf(31, 2, 4, 33, 5, 36),
        intArrayOf(12, 26, 9, 10, 29, 25),
        intArrayOf(13, 17, 21, 22, 20, 18),
        intArrayOf(24, 23, 15, 16, 14, 19),
        intArrayOf(30, 8, 28, 27, 11, 7),
        intArrayOf(1, 35, 34, 3, 32, 6)
    )

    private val table = SummedAreaTable.from(input)

    @Test
    fun getGet() {
        val p00 = Point(0, 0)
        val p23 = Point(2, 3)
        val p44 = Point(4, 4)
        val p55 = Point(5, 5)

        assertThat(table.get(p00, p00)).isEqualTo(31)
        assertThat(table.get(p23, p44)).isEqualTo(listOf(15, 16, 14, 28, 27, 11).sum())
        assertThat(table.get(p00, p55)).isEqualTo(input.flatMap { it.map { it } }.sum())
    }

    @Test
    fun toStringTest() {
        val s = table.toString()

        val expected =
"""  31  33  37  70  75 111
  43  71  84 127 161 222
  56 101 135 200 254 333
  80 148 197 278 346 444
 110 186 263 371 450 555
 111 222 333 444 555 666
"""
        assertThat(s).isEqualTo(expected)
    }

}
