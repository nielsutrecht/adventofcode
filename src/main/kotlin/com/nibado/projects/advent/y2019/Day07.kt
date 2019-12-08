package com.nibado.projects.advent.y2019

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.collect.permutations
import com.nibado.projects.advent.resourceString
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

object Day07 : Day {
    private val program = resourceString(2019, 7).split(",").map { it.toInt() }

    private fun intCode(input: List<Int>) = Day05.IntCode(program.toMutableList(), input.toMutableList())

    private val list = listOf(0, 1, 2, 3, 4).permutations()

    override fun part1(): Int {
        return list.map { (a, b, c, d, e) ->

            val ampA = intCode(listOf(a, 0))
            ampA.run()
            val ampB = intCode(listOf(b, ampA.output.last()))
            ampB.run()
            val ampC = intCode(listOf(c, ampB.output.last()))
            ampC.run()
            val ampD = intCode(listOf(d, ampC.output.last()))
            ampD.run()
            val ampE = intCode(listOf(e, ampD.output.last()))
            ampE.run()
            ampE.output.last()
        }.max()!!
    }

    fun part1b() : Int {
        val perm = (0..4).toList().permutations()




        return perm.map { run2(program, it) }.max()!!
    }

    override fun part2(): Int {
        val perm = (5..9).toList().permutations()




        return perm.map { run2(program, it) }.max()!!
    }

    fun run2(program: List<Int>, settings: List<Int>): Int {
        val (a, b, c, d, e) = settings.map { i -> LinkedBlockingQueue<Int>().also { it.add(i) } }

        a.put(0)

        val ampA = IntCode(program.toMutableList(), a, b)

        val ampB = IntCode(program.toMutableList(), b, c)

        val ampC = IntCode(program.toMutableList(), c, d)

        val ampD = IntCode(program.toMutableList(), d, e)

        val ampE = IntCode(program.toMutableList(), e, a)


        val amps = listOf(ampA, ampB, ampC, ampD, ampE)

        val threads = amps.map {
            thread {
                it.run()
            }
        }

        threads.forEach { it.join() }

        return amps.last().output.last()
    }
}

fun main() {
    println(Day07.part1() == 87138)
    println(Day07.part1b() == 87138)
    println(Day07.part2() == 17279674)
}