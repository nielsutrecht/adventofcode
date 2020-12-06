package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*

typealias Passport = Map<String, String>

object Day04 : Day {
    private val hairColor = "#[0-9a-f]{6}".toRegex()
    private val eyeColor = "amb|blu|brn|gry|grn|hzl|oth".toRegex()
    private val pid = "[0-9]{9}".toRegex()
    private val height = "([0-9]+)(in|cm)".toRegex()

    private val fields = mapOf(
            "byr" to { f: String ->  f.toInt() in 1920 .. 2002},
            "iyr" to { f: String ->  f.toInt() in 2010 .. 2020},
            "eyr" to { f: String ->  f.toInt() in 2020 .. 2030},
            "hgt" to ::heightValid,
            "hcl" to { f: String ->  hairColor.matches(f) },
            "ecl" to { f: String ->  eyeColor.matches(f) },
            "pid" to { f: String ->  pid.matches(f) }
    )

    private val passports = read().filter { pass -> fields.keys.all { pass.containsKey(it) } }

    private fun read() : List<Passport> = resourceStrings(2020, 4)
            .map { pass -> pass.split("[ \\n]".toRegex())
                    .filterNot(String::isBlank)
                    .map { kv -> kv.split(":").let { (k, v) -> k to v } }.toMap() }

    private fun heightValid(hgt: String) : Boolean =
            height.matchEntire(hgt)?.groupValues?.let { (_, a, u) ->
                if(u == "in") {
                    a.toInt() in 59..76
                } else {
                    a.toInt() in 150..193
                }
            } ?: false

    override fun part1(): Int = passports.size
    override fun part2(): Int = passports.count { pass -> fields.all { (k, f) -> f(pass.getValue(k)) } }
}
