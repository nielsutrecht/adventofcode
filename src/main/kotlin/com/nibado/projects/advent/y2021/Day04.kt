package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.*

object Day04 : Day {
    private val values = resourceLines(2021, 4)
    private val numbers = values.first().split(',').map { it.toInt() }
    private val boards = values.asSequence().drop(1).filterNot { it.trim().isBlank() }.chunked(5) {
        Board(it.map { it.trim().split("\\s+".toRegex()).map { it.toInt() } })
    }.toMutableList()

    private val wins = numbers.flatMap { n ->
        boards.forEach { it.mark(n) }
        val won = boards.filter { it.win() }
        boards.removeIf { it.win() }
        won.map { it to n }
    }

    data class Board(val grid: List<List<Int>>, val marked: MutableSet<Point> = mutableSetOf()) {
        fun mark(number: Int) {
            marked += points().filter { (x, y) -> grid[y][x] == number }
        }

        fun rowWin(): Boolean = grid.indices.any { y -> grid[y].indices.all { x -> marked.contains(Point(x, y)) } }
        fun colWin(): Boolean = grid.first().indices.any { x -> grid.indices.all { y -> marked.contains(Point(x, y)) } }
        fun win() = rowWin() or colWin()

        fun points() = grid.indices.flatMap { y -> grid.first().indices.map { x -> Point(x, y) } }
        fun unmarkedSum() = points().filterNot { marked.contains(it) }.map { grid[it.y][it.x] }.sum()
    }

    override fun part1() = wins.first().let { (board, num) -> board.unmarkedSum() * num }
    override fun part2() = wins.last().let { (board, num) -> board.unmarkedSum() * num }
}
