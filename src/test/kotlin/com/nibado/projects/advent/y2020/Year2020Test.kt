package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.TestInput
import com.nibado.projects.advent.YearTest

class Year2020Test : YearTest(input()) {
    companion object {
        fun input() = listOf(
                TestInput(Day01, 838624, 52764180),
                TestInput(Day02, 467, 441),
                TestInput(Day03, 178L, 3492520200),
                TestInput(Day04, 247, 145),
                TestInput(Day05, 935, 743)
        ).drop(0)
    }
}
