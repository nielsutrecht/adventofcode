package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceRegex
import java.lang.Math.abs

object Day20 : Day {
    private val input = resourceRegex(20, Regex("p=<(-?[0-9]+),(-?[0-9]+),(-?[0-9]+)>, v=<(-?[0-9]+),(-?[0-9]+),(-?[0-9]+)>, a=<(-?[0-9]+),(-?[0-9]+),(-?[0-9]+)>"))
            .map { it.subList(1, 10).map { it.toInt() } }
            .map { Particle(it[0], it[1], it[2], it[3], it[4], it[5], it[6], it[7], it[8]) }

    override fun part1(): String {
        val list = input.toMutableList()
        (1..500).forEach { list.forEachIndexed { i, p -> list[i] = p.next() } }

        return list.indexOf(list.sortedBy { it.manhattan }.first()).toString()
    }

    override fun part2(): String {
        val list = input.toMutableList()
        (1..500).forEach {
            list.forEachIndexed { i, p -> list[i] = p.next() }
            val collisions = list.filter { needle -> list.count { needle.collides(it) } > 1 }

            list.removeAll(collisions)
        }

        return list.size.toString()
    }

    data class Particle(val x: Int, val y: Int, val z: Int,
                        val vx: Int, val vy: Int, val vz: Int,
                        val ax: Int, val ay: Int, val az: Int) {

        val manhattan = abs(x) + abs(y) + abs(z)

        fun collides(other: Particle) = other.x == x && other.y == y && other.z == z

        fun next(): Particle {
            val vx = this.vx + ax
            val vy = this.vy + ay
            val vz = this.vz + az

            return Particle(x + vx, y + vy, z + vz, vx, vy, vz, ax, ay, az)
        }
    }
}