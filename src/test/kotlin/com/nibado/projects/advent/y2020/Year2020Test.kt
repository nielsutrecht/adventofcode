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
                TestInput(Day05, 935, 743),
                TestInput(Day06, 7120, 3570),
                TestInput(Day07, 226, 9569),
                TestInput(Day08, 1814, 1056),
                TestInput(Day09, 776203571L, 104800569L),
                TestInput(Day10, 1914, 9256148959232),
                TestInput(Day11, 2251, 2019),
                TestInput(Day12, 1496, 63843),
                TestInput(Day13, 2382, 906332393333683),
                TestInput(Day14, 13865835758282, 4195339838136),
                TestInput(Day15, 1025, 129262),
                TestInput(Day16, 30869, 4381476149273),
                TestInput(Day17, 263, 1680)
        ).drop(0)
    }
}
