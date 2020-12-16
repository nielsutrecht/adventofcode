package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*

object Day16 : Day {
    private val ruleRegex = "([a-z ]+): ([0-9]+)-([0-9]+) or ([0-9]+)-([0-9]+)".toRegex()

    private val values = resourceStrings(2020, 16)
    private val rules = values[0].split("\n").map { it.matchGroups(ruleRegex).drop(1) }.map { groups ->
        groups.let { (name, a1, a2, b1, b2) -> Rule(name, listOf(a1.toInt()..a2.toInt(), b1.toInt()..b2.toInt())) }
    }
    private val myTicket = values[1].split("\n").drop(1).first().let { it.split(",").map { it.toInt() } }
    private val nearBy = values[2].split("\n").drop(1).map { it.split(",").map { it.toInt() } }

    private fun match(value: Int) = rules.any { rule -> rule.ranges.any { value in it } }

    override fun part1() : Int = nearBy.flatten().filterNot { match(it) }.sum()
    override fun part2() : Long {
        val options = myTicket.map { rules.map { it.name }.toMutableSet() }

        nearBy.filterNot { t -> t.any { !match(it) } }.forEach { ticket ->
            ticket.forEachIndexed { index, value ->
                val validRules = rules.filter { r -> r.ranges.any { value in it } }.map { it.name }.toSet()
                options[index].removeIf { !validRules.contains(it) }
            }
        }

        while(options.any { it.size > 1 }) {
            val uniques = options.filter { it.size == 1 }.flatten()
            options.filter { it.size > 1 }.forEach { it.removeAll(uniques) }
        }

        return options.mapIndexedNotNull { index, set -> if(set.first().startsWith("departure")) index else null }
                .map { myTicket[it].toLong() }
                .reduce { a, b -> a * b }

    }

    data class Rule(val name: String, val ranges: List<IntRange>)
}
