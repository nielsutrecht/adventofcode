package com.nibado.projects.advent

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RectangleTest {

    @Test
    fun area() {
        var rect = Rectangle.of(Point(0, 0), 10, 10)
        assertThat(rect.area()).isEqualTo(100)

        rect = Rectangle.of(Point(-10, -10), 10, 10)
        assertThat(rect.area()).isEqualTo(100)

        rect = Rectangle(Point(-10, -10), Point(10, 10))
        assertThat(rect.area()).isEqualTo(400)

        rect = Rectangle(Point(10, 10), Point(-10, -10))
        assertThat(rect.area()).isEqualTo(400)
    }

    @Test
    fun intersect() {
        var rect1 = Rectangle.of(Point(1, 3), 4, 4)
        var rect2 = Rectangle.of(Point(3, 1), 4, 4)
        var rect3 = Rectangle.of(Point(5, 5), 2, 2)

        assertThat(rect1.intersect(rect2)!!.area()).isEqualTo(4)
        assertThat(rect1.intersect(rect3)).isNull()
    }

    @Test
    fun points() {
        val r = Rectangle.of(Point(0, 0), Point(1, 1))

        assertThat(r.points().toList()).containsExactly(Point(0, 0), Point(0, 1), Point(1, 0), Point(1, 1))
    }

    @Test
    fun onEdge() {
        val r = Rectangle.of(Point(0, 0), Point(2, 2))

        assertThat(r.onEdge(Point(1,1))).isFalse()
        assertThat(r.onEdge(Point(0,1))).isTrue()
        assertThat(r.onEdge(Point(2,1))).isTrue()
        assertThat(r.onEdge(Point(1,0))).isTrue()
        assertThat(r.onEdge(Point(1,2))).isTrue()
    }
}