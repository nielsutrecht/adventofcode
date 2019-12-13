package com.nibado.projects.advent.y2019

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines
import kotlin.math.abs

object Day12 : Day {
    private val m = resourceLines(2019, 12)
        .map { it.substringAfter("<").substringBefore(">").split(", ").map { it.substringAfter("=").toInt() } }
        .map { Moon(it[0], it[1], it[2]) }

//    private val moons = listOf(
//        Moon(-1, 0, 2),
//        Moon(2, -10, -7),
//        Moon(4, -8, 8),
//        Moon(3, 5, -1)
//    )

    private val moons = m

    private val pairs = pairs()

    private fun pairs(): List<Pair<Moon, Moon>> {
        val set = mutableSetOf<Pair<Moon, Moon>>()

        for (a in moons) {
            for (b in moons) {
//                if(a != b && !set.contains(a to b) && !set.contains(b to a)) {
//                    set += a to b
//                }
                if (a != b) {
                    set += a to b
                }
            }
        }

        return set.toList()
    }

    override fun part1(): Any {
        //moon has velocity and position
        println("step 0")
        moons.forEach { (p, v) ->
            println("pos=<x=${p.x}, y=${p.y} z=${p.z}>, vel=<x=${v.x}, y=${v.y}, z=${v.z}>")
        }
        for (i in 1..1000) {
            //println("step $i")
            moons.forEach { (p, v) ->
                //println("pos=<x=${p.x}, y=${p.y} z=${p.z}>, vel=<x=${v.x}, y=${v.y}, z=${v.z}>")
            }
            //step time
            //first update the velocity of every moon by applying gravity
            //Then, once all moons' velocities have been updated, update the
            // position of every moon by applying velocity.

            pairs.forEach { adjustVelocity(it) }
            moons.forEach { it.applyVelocity() }
        }

        println(moons.map { it.total }.sum())
        return ""
    }

    override fun part2(): Any {
        //moon has velocity and position
        println("step 0")
        moons.forEach { (p, v) ->
            println("pos=<x=${p.x}, y=${p.y} z=${p.z}>, vel=<x=${v.x}, y=${v.y}, z=${v.z}>")
        }

        val xCycles = mutableSetOf<List<Long>>()
        val yCycles = mutableSetOf<List<Long>>()
        val zCycles = mutableSetOf<List<Long>>()

        for (i in 1..1000000000) {
            //println("step $i")
            moons.forEach { (p, v) ->
                //println("pos=<x=${p.x}, y=${p.y} z=${p.z}>, vel=<x=${v.x}, y=${v.y}, z=${v.z}>")
            }
            //step time
            //first update the velocity of every moon by applying gravity
            //Then, once all moons' velocities have been updated, update the
            // position of every moon by applying velocity.

            pairs.forEach { adjustVelocity(it) }
            moons.forEach { it.applyVelocity() }

            val xCycle = moons.map { (a, _) -> a.x }
            val yCycle = moons.map { (a, _) -> a.y }
            val zCycle = moons.map { (a, _) -> a.z }

            if(xCycles.contains(xCycle)) {
                println("X${i - 1}")
            }
            if(yCycles.contains(yCycle)) {
                println("Y${i - 1}")
            }
            if(zCycles.contains(zCycle)) {
                println("Z${i - 1}")
            }

            xCycles += xCycle
            yCycles += yCycle
            zCycles += zCycle
        }

        return ""
    }

    private fun adjustVelocity(pair: Pair<Moon, Moon>) {
        val pA = pair.first.position
        val pB = pair.second.position

        fun adjust(f: (Point3D) -> Long): Pair<Long, Long> =
            when {
                f(pA) > f(pB) -> -1L to 1L
                f(pA) < f(pB) -> 1L to -1L
                else -> 0L to 0L
            }

        val (xA, xB) = adjust { it.x }
        val (yA, yB) = adjust { it.y }
        val (zA, zB) = adjust { it.z }

        pair.first.velocity += Point3D(xA, yA, zA)
        //pair.second.velocity += Point3D(xB, yB, zB)
    }

    fun test() {
        val g = Moon(3, 0, 0)
        val c = Moon(5, 0, 0)

        adjustVelocity(g to c)
        println(g)
        println(c)
    }

    data class Moon(var position: Point3D, var velocity: Point3D = Point3D(0, 0, 0)) {
        constructor(x: Int, y: Int, z: Int) : this(Point3D(x.toLong(), y.toLong(), z.toLong()))

        fun applyVelocity() {
            position += velocity
        }

        val potential: Long
            get() = abs(position.x) + abs(position.y) + abs(position.z)

        val kinetic: Long
            get() = abs(velocity.x) + abs(velocity.y) + abs(velocity.z)

        val total: Long
            get() = potential * kinetic
    }

    data class Point3D(val x: Long, val y: Long, val z: Long) {
        operator fun plus(other: Point3D) = Point3D(x + other.x, y + other.y, z + other.z)
    }
}

fun main() {
    //Day12.test()
    Day12.part2()
}