package com.nibado.projects.advent.collect

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class StackTest {
    @Test
    fun `Should have correct size`() {
        val stack1 = Stack<Int>()

        assertThat(stack1).hasSize(0)
        stack1.push(1)
        assertThat(stack1).hasSize(1)
        stack1.pop()
        assertThat(stack1).hasSize(0)

        assertThat(Stack(1, 3, 4)).hasSize(3)
        assertThat(Stack(listOf(1, 3, 4))).hasSize(3)
    }

    @Test
    fun `Should iterate in LIFO order`() {
        val stack = Stack<Int>()

        stack += 1
        stack += 2
        stack += 3

        assertThat(stack.iterator().asSequence().toList()).containsExactly(3, 2, 1)
        assertThat(stack.toList()).containsExactly(3, 2, 1)
    }
}
