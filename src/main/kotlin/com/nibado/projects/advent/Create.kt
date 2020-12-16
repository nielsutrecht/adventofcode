package com.nibado.projects.advent

import java.io.File
import java.time.LocalDate

object Create {
    @JvmStatic
    fun main(args: Array<String>) {
        val year = if(args.isNotEmpty()) args.first().toInt() else LocalDate.now().year

        if(year !in 2015 .. LocalDate.now().year) {
            throw IllegalArgumentException("$year should be in 2015 .. ${LocalDate.now().year}")
        }

        createAll(year)
    }

    private fun createAll(year: Int) {
        val now = LocalDate.now()
        val days = (1 .. 25).map { LocalDate.of(year, 12, it) }.filter { it <= now }

        days.forEach {
            print("$it ")
            create(it)
        }
    }

    private fun create(date: LocalDate) {
        val dayPadded = date.dayOfMonth.toString().padStart(2, '0')

        val kotlinFile = File("$KOTLIN_ROOT/y${date.year}/Day$dayPadded.kt")

        if(kotlinFile.exists()) {
            println("skipped")
            return
        }

        kotlinFile.writeText(TEMPLATE.format(date.year, dayPadded, date.year, date.dayOfMonth))

        val textFile = File("$TEXT_ROOT/${date.year}/day$dayPadded.txt")

        if(!textFile.exists()) {
            textFile.createNewFile()
        }

        println("created")
    }

    private const val KOTLIN_ROOT = "src/main/kotlin/com/nibado/projects/advent"
    private const val TEXT_ROOT = "src/main/resources"
    private const val TEMPLATE = """
package com.nibado.projects.advent.y%s

import com.nibado.projects.advent.*

object Day%s : Day {
    private val values = resourceLines(%s, %s)

    override fun part1() = TODO()
    override fun part2() = TODO()
}
    """
}
