package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*

object Day19 : Day {
    private val values = resourceStrings(2020, 19)
    private val rules1 = values.first().split("\n")
            .map { it.split(":").let { (id, rule) -> id.toInt() to rule.trim() } }
            .toMap()
    private val rules2 = rules1 + listOf(8 to "42 | 42 8", 11 to "42 31 | 42 11 31").toMap()
    private val lines = values[1].split("\n")

    override fun part1() = lines.count { isMatch(rules1, it, listOf(0)) }
    override fun part2() = lines.count { isMatch(rules2, it, listOf(0)) }

    private fun isMatch(ruleMap: Map<Int, String>, line: CharSequence, rules: List<Int>): Boolean {
        if (line.isEmpty()) {
            return rules.isEmpty()
        } else if (rules.isEmpty()) {
            return false
        }
        return ruleMap.getValue(rules[0]).let { rule ->
            if (rule[1] in 'a'..'z') {
                if (line.startsWith(rule[1])) {
                    isMatch(ruleMap, line.drop(1), rules.drop(1))
                } else false
            } else rule.split(" | ").any {
                isMatch(ruleMap, line, it.split(" ").map(String::toInt) + rules.drop(1))
            }
        }
    }
}
