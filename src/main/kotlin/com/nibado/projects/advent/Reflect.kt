package com.nibado.projects.advent

import org.reflections.Reflections

object Reflect {
    private val classNameRegex = "com.nibado.projects.advent.y[0-9]{4}.Day[0-9]{2}".toRegex()
    fun getDays(year: Int) : List<Day> {
        val reflections =  Reflections("com.nibado.projects.advent.y$year")

        return reflections.getSubTypesOf(Day::class.java)
            .filter { it.name.matches(classNameRegex) }
            .mapNotNull { it.kotlin.objectInstance }
            .sortedBy { it.javaClass.simpleName }
    }
}

fun main() {
    val days = Reflect.getDays(2019)

    days.forEach { println(it.javaClass) }
}