package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*

object Day21 : Day {
    private val values = resourceRegex(2020, 21, "([a-z ]+) \\(contains ([a-z, ]+)\\)").map { (_, ing, all) ->
        ing.split(" ").toSet() to all.split(", ").toSet()
    }

    private val allergens : Map<String, String> by lazy {
        val allergens = values.flatMap { (_,a) -> a }.toSet()

        val allergenMap = allergens.map { allergen ->
            allergen to values.filter { (_,a) ->a.contains(allergen) }.map { (i) -> i }.reduce { a, b -> a.intersect(b) }
        }.toMap().toMutableMap()

        while(allergenMap.any { it.value.size > 1 }) {
            allergenMap.filter { it.value.size == 1 }.forEach { (_, single) ->
                allergenMap.filter { it.value.size > 1 }.forEach { (a, _) ->
                    allergenMap[a] = allergenMap[a]!! - single
                }
            }
        }
        allergenMap.map { (a, i) -> a to i.first() }.toMap()
    }

    override fun part1() : Int {
        val safe = values.flatMap { it.first }.toSet() - allergens.map { it.value }
        return values.flatMap { it.first.map { if(safe.contains(it)) 1 else 0 } }.sum()
    }
    override fun part2() : String = allergens.entries.sortedBy { (a, _) -> a }.joinToString(",") { (_, i) -> i}
}