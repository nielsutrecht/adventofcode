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
            TestInput(Day07, 335271, 95851339),
            TestInput(Day08, 452, 1096964),
            TestInput(Day09, 591, 1113424),
            TestInput(Day10, 394647, 2380061249L),
            TestInput(Day11, 1613, 510),
            TestInput(Day12, 3563, 105453),
            TestInput(Day13, 716, Year2021Results.DAY13_P2),
            TestInput(Day14, 5656L, 12271437788530L),
            TestInput(Day15, 790, 2998),
            TestInput(Day16, 945, 10637009915279L),
            TestInput(Day17, 5995, 3202),
            TestInput(Day18, 3654, 4578),
        ).drop(17)
    }
}

object Year2021Results {
    val DAY13_P2 = """
        ###..###...##..#..#.####.###..#....###.
        #..#.#..#.#..#.#.#..#....#..#.#....#..#
        #..#.#..#.#....##...###..###..#....#..#
        ###..###..#....#.#..#....#..#.#....###.
        #.#..#....#..#.#.#..#....#..#.#....#.#.
        #..#.#.....##..#..#.#....###..####.#..#
         """.trimIndent()
}
