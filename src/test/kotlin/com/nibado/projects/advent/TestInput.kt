package com.nibado.projects.advent

import java.io.*

object TestResource {
    fun resource(name: String): InputStream {
        return String::class.java.getResourceAsStream("/testinput/$name") ?: fileResource(name)
    }

    private fun fileResource(name: String) : InputStream {
        val file = File("./src/test/resources/testinput/$name")

        if(!file.exists()) {
            throw IllegalArgumentException("File $file does not exist")
        }

        return file.inputStream()
    }

    fun resourceLines(name: String): List<String> {
        return resource(name).bufferedReader().readLines()
    }
}