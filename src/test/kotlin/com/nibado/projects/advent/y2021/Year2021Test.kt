package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.*

class Year2021Test : YearTest(input()) {
    companion object {
        fun input() = listOf(
                TestInput(Day01, 1624, 1653),
                TestInput(Day02, 1989014L, 2006917119L),
                TestInput(Day03, 1997414L, 1032597L),
                TestInput(Day04, 55770, 2980),
                TestInput(Day05, 6710, 20121),
                TestInput(Day06, 386536L, 1732821262171L),
                TestInput(Day07, 335271, 95851339)
        ).drop(6)
    }
}
