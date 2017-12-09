package com.nibado.projects.advent.y2016

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceRegex

object Day10 : Day {
    private val regex = Regex("(value ([0-9]+) goes to bot ([0-9]+))|(bot ([0-9]+) gives low to (output|bot) ([0-9]+) and high to (output|bot) ([0-9]+))")
    private val input = resourceRegex(2016, 10, regex).map { it.filterNot { it.isEmpty() || it.length > 10 } }.groupBy { it.size == 2 }

    override fun part1() : String {
        val bots = input[false]!!.map { bot(it) }.toMutableList()
        input[true]!!.map { { State.valueToBot(it[0].toInt(), it[1].toInt()) }}.forEach { it() }

        while(bots.isNotEmpty()) {
            bots.removeIf { it.transfer() }
        }

        return State.luckyBot!!.num.toString()
    }

    override fun part2() = (State.outputs[0]!! * Day10.State.outputs[1]!! * Day10.State.outputs[2]!!).toString()

    private fun bot(instructions: List<String>): Bot {
        val botNum = instructions[0].toInt()
        val lowTarget = instructions[2].toInt()
        val highTarget = instructions[4].toInt()

        val transferLow : (Int) -> Unit = if(instructions[1] == "bot")
            { a -> State.bot(lowTarget).values += a }
        else
            { a -> State.outputs[lowTarget]= a }

        val transferHigh : (Int) -> Unit = if(instructions[3] == "bot")
            { a -> State.bot(highTarget).values += a }
        else
            { a -> State.outputs[highTarget]= a }

        val bot = Bot(botNum, transferLow, transferHigh)

        State.bots[botNum] = bot

        return bot
    }

    object State {
        val bots = mutableMapOf<Int, Bot>()
        val outputs = mutableMapOf<Int, Int>()
        var luckyBot: Bot? = null

        fun valueToBot(value: Int, bot: Int) { bot(bot).values += value }
        fun bot(bot: Int) = bots[bot]!!
    }

    data class Bot(
            val num: Int,
            private val transferLow: (Int) -> Unit,
            private val transferHigh: (Int) -> Unit
    ) {
        val values: MutableList<Int> = mutableListOf()

        fun transfer() : Boolean {
            return if(values.size != 2) {
                false
            } else {
                if(values.contains(61) && values.contains(17)) {
                    State.luckyBot = this
                }
                values.sort()
                transferLow(values[0])
                transferHigh(values[1])

                true
            }
        }
    }
}