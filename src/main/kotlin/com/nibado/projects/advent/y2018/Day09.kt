package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Day
import java.util.*

object Day09 : Day {
    class Board : ArrayDeque<Int>() {
        fun rotate(amount: Int) {
            if (amount >= 0) {
                for (i in 0 until amount) {
                    addFirst(removeLast())
                }
            } else {
                for (i in 0 until -amount - 1) {
                    addLast(remove())
                }
            }
        }
    }

    private fun game(players: Int, marbleMaxValue: Int): Long {
        val board = Board()
        val scores = LongArray(players)
        board.addFirst(0)

        for (marble in (1..marbleMaxValue)) {
            if (marble % 23 == 0) {
                board.rotate(-7)
                scores[marble % players] += board.pop().toLong() + marble

            } else {
                board.rotate(2)
                board.addLast(marble)
            }
        }

        return scores.max()!!
    }

    override fun part1() = game(459, 71320)
    override fun part2() = game(459, 71320 * 100)
}