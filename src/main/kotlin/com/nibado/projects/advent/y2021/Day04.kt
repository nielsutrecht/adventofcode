package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Point
import com.nibado.projects.advent.resourceLines

object Day04 : Day {
    private val values = resourceLines(2021, 4)
    private val numbers = values.first().split(',').map { it.toInt() }
    private val boards = values.asSequence().drop(1).filterNot { it.trim().isBlank() }.chunked(5) {
        Board(it.map { it.trim().split("\\s+".toRegex()).map { it.toInt() } })
    }.toList()

    data class Board(val grid: List<List<Int>>, val marked: MutableSet<Point> = mutableSetOf()) {
        fun mark(number: Int) {
            marked += points().filter { (x, y) -> grid[y][x] == number}
        }

        fun rowWin() : Boolean  = grid.indices.any { y -> grid[y].indices.all { x -> marked.contains(Point(x,y)) } }
        fun colWin() :Boolean = grid.first().indices.any { x -> grid.indices.all { y -> marked.contains(Point(x, y)) } }
        fun win() = rowWin() or colWin()

        fun points() = grid.indices.flatMap { y -> grid.first().indices.map { x -> Point(x, y) } }
        fun unmarkedSum() = points().filterNot { marked.contains(it) }.map { grid[it.y][it.x] }.sum()
    }

    override fun part1() : Any {
        for(n in numbers) {
            boards.forEach {
                it.mark(n)
            }
            val wins = boards.filter { it.win() }
            if(wins.isNotEmpty()) {
                wins.forEach { println(it)  }
                return wins.first().unmarkedSum().toLong() * n
            }
        }
        throw IllegalStateException()
    }

    override fun part2() : Any {
        boards.forEach { it.marked.clear() }

        val notWon = boards.toMutableList()

        val seq = numbers.toMutableList()
        var lastNumber = 0
        while(notWon.size > 1) {
            lastNumber = seq.removeFirst()
            notWon.forEach {
                it.mark(lastNumber)
            }
            notWon.removeIf { it.win() }
            notWon.size > 1
        }

        val last = notWon.first()
        while(!last.win()) {
            lastNumber = seq.removeFirst()
            last.mark(lastNumber)
        }

        return lastNumber.toLong() * last.unmarkedSum()
    }
}