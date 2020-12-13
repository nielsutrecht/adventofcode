package com.nibado.projects.advent

import com.nibado.projects.advent.Point.Companion.parse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PointTest {
    @Test
    fun manhattan() {
        val p00 = 0 point 0
        val p55 = 5 point 5
        val pm55 = -5 point -5

        assertThat(p00.manhattan(p00)).isEqualTo(0)
        assertThat(p55.manhattan(p55)).isEqualTo(0)

        assertThat(p00.manhattan(p55)).isEqualTo(10)
        assertThat(p55.manhattan(p00)).isEqualTo(10)
        assertThat(p00.manhattan(pm55)).isEqualTo(10)
        assertThat(pm55.manhattan(p00)).isEqualTo(10)

        assertThat(pm55.manhattan(p55)).isEqualTo(20)
        assertThat(p55.manhattan(pm55)).isEqualTo(20)
    }

    @Test
    fun parse() {
        assertThat(parse("1,1")).isEqualTo(1 point 1)
        assertThat(parse("1;1")).isEqualTo(1 point 1)
        assertThat(parse("1, 1")).isEqualTo(1 point 1)
        assertThat(parse("1:    1")).isEqualTo(1 point 1)
        assertThat(parse("59, 110")).isEqualTo(59 point 110)
    }

    @Test
    fun pairToPoint() {
        val p = (1 to 2).toPoint()

        assertThat(p).isEqualTo(1 point 2)
    }

    @Test
    fun plus() {
        val p = 1 point 1

        val t1 = p + (1 point -2)
        val t2 = p + (1 to -2)
        val t3 = p + Direction.SOUTH

        assertThat(t1).isEqualTo(2 point -1)
        assertThat(t2).isEqualTo(2 point -1)
        assertThat(t3).isEqualTo(1 point 2)
    }

    @Test
    fun rotate() {
        val initial = Point(1, 0)

        assertThat(initial.rotate90()).isEqualTo(0 point 1)
        assertThat(initial.rotate(90)).isEqualTo(0 point 1)
        assertThat(initial.rotate(180)).isEqualTo(-1 point 0)
        assertThat(initial.rotate(270)).isEqualTo(0 point -1)
        assertThat(initial.rotate(360)).isEqualTo(1 point 0)
    }
}
