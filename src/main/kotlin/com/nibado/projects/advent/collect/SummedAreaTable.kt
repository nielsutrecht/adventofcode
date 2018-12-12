package com.nibado.projects.advent.collect

import com.nibado.projects.advent.Point

// https://en.wikipedia.org/wiki/Summed-area_table
class SummedAreaTable private constructor(private val table: List<IntArray>) {
    private fun get(p: Point) = get(p.x, p.y)
    private fun get(x: Int, y: Int) = if(x < 0 || y < 0) { 0 } else table[y][x]

    fun get(a: Point, b: Point) = get(a.x, a.y, b.x, b.y)

    fun get(x1: Int, y1: Int, x2: Int, y2: Int): Int {
        val a = Point(x1 - 1, y1 - 1)
        val b = Point(x2, y1 - 1)
        val c = Point(x1 - 1, y2)
        val d = Point(x2, y2)

        return get(a) + get(d) - get(b) - get(c)
    }

    override fun toString() = toString(4)

    fun toString(width: Int): String {
        val b = StringBuilder()
        for (y in table.indices) {
            for (x in table[0].indices) {
                b.append("%1\$${width}s".format(table[y][x]))
            }
            b.append('\n')
        }

        return b.toString()
    }

    companion object {
        fun from(table: List<IntArray>): SummedAreaTable {
            return SummedAreaTable(build(table))
        }

        private fun build(table: List<IntArray>) : List<IntArray> {
            val output = table.indices.map { IntArray(table[0].size) }.toList()

            for (y in table.indices) {
                for (x in table[0].indices) {
                    output[y][x] = table[y][x]
                    if (y > 0)
                        output[y][x] += output[y - 1][x]
                    if (x > 0) {
                        output[y][x] += output[y][x - 1]
                        if (y > 0)
                            output[y][x] -= output[y - 1][x - 1] // because this is added twice in above two additions
                    }
                }
            }

            return output
        }
    }
}





