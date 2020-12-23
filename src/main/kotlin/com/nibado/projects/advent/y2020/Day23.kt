
package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*
import com.nibado.projects.advent.collect.CircularList

object Day23 : Day {
    //ex: 389125467
    //input: 589174263
    private val input = "589174263".split("").filterNot { it.isBlank() }.map { it.toInt() }

    override fun part1() : Int {
        val game = runGame(CircularList(input), 100)

        val indexOfOne = game.indexOf(1)

        val result = (1 .. game.size - 1).joinToString("") { game.get(indexOfOne + it).toString() }

        return result.toInt()
    }
    override fun part2() : Int = TODO()

    private fun runGame(game: CircularList<Int>, repeats: Int) : CircularList<Int> {
        repeat(repeats) {

            val selected = game.current()
            val next = game.next(4)
            game.right(1)

            val taken = game.remove(3)


            val available = game.toList().sorted()
            val find = available[(available.size + available.indexOf(selected) - 1) % available.size].let {
                it to game.indexOf(it)
            }


            game.seek(find.second)
            game.insertAfter(taken)
            game.seek(game.indexOf(next))
        }

        return game
    }
}

fun main() {
    println(Day23.part1()) //43896725
    //println(Day23.part2())
}
    