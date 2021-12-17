package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.*
import kotlin.math.max

object Day17 : Day {
    private val target = resourceRegex(2021, 17, "target area: x=([-0-9]+)..([-0-9]+), y=([-0-9]+)..([-0-9]+)")
        .let { it.first().drop(1).map { it.toInt() }.let { (x1, x2, y1, y2) -> Rectangle(x1, y1, x2, y2) } }

    private val results: List<Pair<Boolean, Int>> by lazy {
        val velocities = (target.minY .. 250).flatMap { y -> (1 .. target.maxX).map { x -> Point(x, y) } }
        velocities.map { hit(Probe(velX = it.x, velY = it.y)) }
            .filter { it.first }
    }

    private fun hit(probe: Probe) : Pair<Boolean, Int> {
        var maxY = probe.pos.y
        while(probe.pos.y >= target.minY) {
            maxY = max(maxY, probe.pos.y)
            if(target.inBounds(probe.pos)) {
                return true to maxY
            }
            probe.pos += Point(probe.velX, probe.velY)
            if(probe.velX > 0) {
                probe.velX--
            }
            probe.velY--
        }
        return false to maxY
    }

    override fun part2() = results.size
    override fun part1() = results.maxOf { it.second }

    data class Probe(var pos: Point = Point.ORIGIN, var velX: Int, var velY: Int)
}