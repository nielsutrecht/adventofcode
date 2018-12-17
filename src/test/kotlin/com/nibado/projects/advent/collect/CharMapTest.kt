package com.nibado.projects.advent.collect

import com.nibado.projects.advent.Point
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class CharMapTest {
    @Test
    fun testDraw() {
        val map = CharMap(5, 5, '.')

        (0..4).forEach { map[it, it] = 'X' }

        map.drawHorizontal(0, 0..4, '#')
        map.drawHorizontal(Point(0, 4), 5, '#')
        map.drawVertical(0, 0 until 5, '#')
        map.drawVertical(Point(4, 0), 5, '#')

        assertThat(map.toString().trim()).isEqualTo("""
            #####
            #X..#
            #.X.#
            #..X#
            #####
        """.trimIndent())

        assertThat(map.toString(Point(3,3), Point(4,4)).trim()).isEqualTo("""
            X#
            ##
        """.trimIndent())
    }
}