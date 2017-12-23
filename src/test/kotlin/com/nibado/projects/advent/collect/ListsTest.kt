package com.nibado.projects.advent.collect

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ListsTest {
    @Test
    fun reverse() {
        val input = listOf(1, 2, 3, 4, 5)

        assertThat(input.reverse( 1, 2)).isEqualTo(listOf(1, 3, 2, 4, 5))
        assertThat(input.reverse(4, 2)).isEqualTo(listOf(5, 2, 3, 4, 1))
        assertThat(input.reverse(0, 5)).isEqualTo(listOf(5, 4, 3, 2, 1))
        assertThat(input.reverse(2, 5)).isEqualTo(listOf(4, 3, 2, 1, 5))

        val inputStrings= listOf("a", "b", "c", "d", "e")

        assertThat(inputStrings.reverse(0, 5)).isEqualTo(inputStrings.reversed())
    }

    @Test
    fun rotateLeft() {
        val input = listOf(1, 2, 3, 4, 5)

        assertThat(input.rotateLeft(2)).isEqualTo(listOf(3, 4, 5, 1, 2))
        assertThat(input.rotateLeft(5)).isEqualTo(input)

        val inputStrings= listOf("a", "b", "c", "d", "e")
        assertThat(inputStrings.rotateLeft(5)).isEqualTo(inputStrings)
    }

    @Test
    fun rotateRight() {
        val input = listOf(1, 2, 3, 4, 5)

        assertThat(input.rotateRight(2)).isEqualTo(listOf(4, 5, 1, 2, 3))
        assertThat(input.rotateRight(5)).isEqualTo(input)

        val inputStrings= listOf("a", "b", "c", "d", "e")
        assertThat(inputStrings.rotateRight(5)).isEqualTo(inputStrings)
    }
}