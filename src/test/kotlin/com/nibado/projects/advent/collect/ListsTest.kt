package com.nibado.projects.advent.collect

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ListsTest {
    @Test
    fun reverse() {
        val input = listOf(1, 2, 3, 4, 5)

        assertThat(reverse(input, 1, 2)).isEqualTo(listOf(1, 3, 2, 4, 5))
        assertThat(reverse(input, 4, 2)).isEqualTo(listOf(5, 2, 3, 4, 1))
        assertThat(reverse(input, 0, 5)).isEqualTo(listOf(5, 4, 3, 2, 1))
        assertThat(reverse(input, 2, 5)).isEqualTo(listOf(4, 3, 2, 1, 5))

        val inputStrings= listOf("a", "b", "c", "d", "e")

        assertThat(reverse(inputStrings, 0, 5)).isEqualTo(inputStrings.reversed())


    }

    @Test
    fun rotateLeft() {
        val input = listOf(1, 2, 3, 4, 5)

        assertThat(rotateLeft(input, 2)).isEqualTo(listOf(3, 4, 5, 1, 2))
        assertThat(rotateLeft(input, 5)).isEqualTo(input)

        val inputStrings= listOf("a", "b", "c", "d", "e")
        assertThat(rotateLeft(inputStrings, 5)).isEqualTo(inputStrings)
    }

    @Test
    fun rotateRight() {
        val input = listOf(1, 2, 3, 4, 5)

        assertThat(rotateRight(input, 2)).isEqualTo(listOf(4, 5, 1, 2, 3))
        assertThat(rotateRight(input, 5)).isEqualTo(input)

        val inputStrings= listOf("a", "b", "c", "d", "e")
        assertThat(rotateRight(inputStrings, 5)).isEqualTo(inputStrings)
    }
}