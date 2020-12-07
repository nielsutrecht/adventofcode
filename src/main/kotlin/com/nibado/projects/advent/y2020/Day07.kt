package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceRegex

object Day07 : Day {
    private val REGEX_LINES = "([a-z ]+) bags contain ([0-9a-z, ]+)\\.".toRegex()
    private val REGEX_BAG = "([0-9]+) ([a-z ]+) (bag|bags)".toRegex()
    private val bags = resourceRegex(2020, 7, REGEX_LINES).map { (_, a, b) -> parseBag(a, b) }

    override fun part1() : Int {
        val consider = mutableListOf<Bag>()
        val found = mutableSetOf<Bag>()

        consider += search("shiny gold")
        found += consider

        while(consider.isNotEmpty()) {
            val new = consider.flatMap { search(it.color) }
            consider.clear()
            consider += new.filterNot { found.contains(it) }
            found += new
        }

        return found.size
    }

    override fun part2() : Int = count("shiny gold") - 1

    private fun count(color: String) : Int = 1 + bags.first { it.color == color }.bags
            .map { (c, bag) -> c * count(bag.color) }.sum()

    private fun search(color: String) : List<Bag> = bags.filter { it.bags.any { it.second.color == color } }

    private fun parseBag(first: String, second: String) : Bag {
        val list = if(second == "no other bags") {
            emptyList()
        } else {
            second.split(", ").map { REGEX_BAG.matchEntire(it)!!.groupValues
                    .let { (_, a, b) -> a.toInt() to Bag(b, emptyList()) } }
        }
        return Bag(first, list)
    }

    data class Bag(val color: String, val bags: List<Pair<Int, Bag>>)
}
