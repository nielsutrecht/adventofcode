package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines

object Day18 : Day {
    private val input = resourceLines(18).map { it.split(" ") }.map { listOf(it[0], it[1], if(it.size == 3) it[2] else "") }
    override fun part1() :String {
        val registers = mutableMapOf<String, Long>()
        val sounds = mutableListOf<Long>()

        fun value(s: String) = if(s[0].isLetter()) registers.computeIfAbsent(s, {0L}) else s.toLong()

        var index = 0
        while(index < input.size) {
            val (op, reg1, reg2) = input[index]
            when(op) {
                "set" -> { registers[reg1] = value(reg2)}
                "mul" -> { registers[reg1] = value(reg1) * value(reg2)}
                "add" -> { registers[reg1] = value(reg1) + value(reg2)}
                "mod" -> { registers[reg1] = value(reg1) % value(reg2)}
                "jgz" -> {
                    if(value(reg1) > 0) {
                        index += value(reg2).toInt() - 1
                    }
                }

                "snd" -> { sounds += value(reg1) }
                "rcv" -> {
                    if(value(reg1) != 0L) {
                        registers[reg1] = sounds.last()
                        return sounds.last().toString()
                    }
                }
            }

            index++
        }
        throw RuntimeException("Should not happen")
    }

    override fun part2() :String {
        val p0 = Program(0)
        val p1 = Program(1)

        p0.otherQueue = p1.queue
        p1.otherQueue = p0.queue

        while(true) {
            if(p0.tick() && p1.tick()) {
                break
            }
        }

        return p1.count.toString()
    }

    class Program(num: Int) {
        val queue = mutableListOf<Long>()
        lateinit var otherQueue : MutableList<Long>
        private val registers = mutableMapOf("p" to num.toLong())
        var index = 0
        var count = 0

        private fun value(s: String) = if(s[0].isLetter()) registers.computeIfAbsent(s, {0L}) else s.toLong()

        fun tick(): Boolean {
            val (op, reg1, reg2) = input[index]
            when(op) {
                "set" -> { registers[reg1] = value(reg2)}
                "mul" -> { registers[reg1] = value(reg1) * value(reg2)}
                "add" -> { registers[reg1] = value(reg1) + value(reg2)}
                "mod" -> { registers[reg1] = value(reg1) % value(reg2)}
                "jgz" -> {
                    if(value(reg1) > 0) {
                        index += value(reg2).toInt() - 1
                    }
                }

                "snd" -> {
                    otherQueue.add(value(reg1))
                    count++
                }
                "rcv" -> {
                    if(queue.isNotEmpty()) {
                        registers[reg1] = queue.removeAt(0)
                    } else {
                        return true
                    }
                }
            }

            index++
            return false
        }
    }
}