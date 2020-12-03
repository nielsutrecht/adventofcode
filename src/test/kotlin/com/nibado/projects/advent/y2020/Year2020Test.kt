package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.TestInput
import com.nibado.projects.advent.YearTest

class Year2020Test : YearTest(input()) {
    companion object {
        fun input() = listOf(
                TestInput(Day01, 838624, 52764180),
                TestInput(Day02, 467, 441),
                TestInput(Day03, 178, 3492520200)
        ).drop(0)
    }
}
