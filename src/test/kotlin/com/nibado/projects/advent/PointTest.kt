package com.nibado.projects.advent

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PointTest {
    @Test
    fun manhattan() {
        val p00 = Point(0, 0)
        val p55 = Point(5, 5)
        val pm55 = Point(-5, -5)

        assertThat(p00.manhattan(p00)).isEqualTo(0)
        assertThat(p55.manhattan(p55)).isEqualTo(0)

        assertThat(p00.manhattan(p55)).isEqualTo(10)
        assertThat(p55.manhattan(p00)).isEqualTo(10)
        assertThat(p00.manhattan(pm55)).isEqualTo(10)
        assertThat(pm55.manhattan(p00)).isEqualTo(10)

        assertThat(pm55.manhattan(p55)).isEqualTo(20)
        assertThat(p55.manhattan(pm55)).isEqualTo(20)
    }
}