package com.nibado.projects.advent.y2019

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.collect.permutations
import com.nibado.projects.advent.resourceString
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

object Day07 : Day {
    private val program = resourceString(2019, 7).split(",").map { it.toInt() }

    override fun part1() = (0..4).toList().permutations()
            .map { run(program, it) }.max()!!

    override fun part2() = (5..9).toList().permutations()
            .map { run(program, it) }.max()!!


    private fun run(program: List<Int>, settings: List<Int>): Int {
        val queues = settings.map { i -> LinkedBlockingQueue<Int>().also { it.add(i) } }

        queues.first().put(0)

        val amps = (0..4).map { i -> IntCode(program.toMutableList(), queues[i], queues[(i + 1) % 5]) }
        val threads = amps.map { thread { it.run() } }

        threads.forEach { it.join() }

        return amps.last().output.last()
    }
}