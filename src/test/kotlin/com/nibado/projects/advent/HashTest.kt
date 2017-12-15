package com.nibado.projects.advent

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class HashTest {
    @Test
    fun md5() {
        assertThat(Hash.md5("a")).matches("[0-9a-f]{32}")
        assertThat(Hash.md5("a")).isEqualTo(Hash.md5("a"))
        assertThat(Hash.md5("a")).isNotEqualTo(Hash.md5("b"))
    }
}