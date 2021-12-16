package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.*
import com.nibado.projects.advent.collect.StringTokenizer

object Day16 : Day {
    private val binary = resourceString(2021, 16).trim().chunked(2)
        .map { it.toUByte(16).toString(2).padStart(8, '0') }.joinToString("")
    private val tree: Packet by lazy { parse(StringTokenizer(binary)) }

    private fun parse(tok: StringTokenizer, packet: Packet? = null) : Packet {
        val version = tok.take(3).toInt(2)
        val type = tok.take(3).toInt(2).let { PacketType.values()[it] }
        var current = packet
        if(type == PacketType.LIT) {
            var number = ""
            do {
                val v = tok.take(5)
                number += v.substring(1)
            } while(v.first() == '1')
            val lit = Packet(version, type, number.toLong(2))
            if(current == null) {
                current = lit
            } else {
                current.sub += lit
            }
        } else {
            val new = Packet(version, type)
            if(current != null) {
                current.sub += new
            }
            current = new

            val lenId = tok.take(1).toInt(2)
            val length = tok.take(if(lenId == 0) 15 else 11).toInt(2)
            if(lenId == 0) {
                val readStart = tok.read
                while(tok.read - readStart < length) {
                    parse(tok, current)
                }
            } else {
                repeat(length) {
                    parse(tok, current)
                }
            }
        }

        return current
    }

    data class Packet(val version: Int, val type: PacketType, val value: Long? = null, val sub: MutableList<Packet> = mutableListOf()) {
        val versionSum: Int
                get() = version + sub.sumOf { it.versionSum }

        fun eval() : Long = when(type) {
            PacketType.LIT -> value!!
            PacketType.SUM -> sub.sumOf { it.eval() }
            PacketType.PROD -> sub.fold(1L) { acc, n -> acc * n.eval() }
            PacketType.MIN -> sub.minOf { it.eval() }
            PacketType.MAX -> sub.maxOf { it.eval() }
            PacketType.GT -> sub.let { (a, b) -> if(a.eval() > b.eval()) 1 else 0 }
            PacketType.LT -> sub.let { (a, b) -> if(a.eval() < b.eval()) 1 else 0 }
            PacketType.EQ -> sub.let { (a, b) -> if(a.eval() == b.eval()) 1 else 0 }
        }
    }

    enum class PacketType {
        SUM,
        PROD,
        MIN,
        MAX,
        LIT,
        GT,
        LT,
        EQ
    }

    override fun part1() : Int = tree.versionSum
    override fun part2() : Long = tree.eval()
}