package com.nibado.projects.advent

interface Day : Runnable {
    override fun run() {
        main(arrayOf())
    }

    fun main(args : Array<String>)
}