package com.nibado.projects.advent.y2018

object Day10Ocr {
    private val G = """
    .####.
    #....#
    #.....
    #.....
    #.....
    #..###
    #....#
    #....#
    #...##
    .###.#""".trimIndent()

    private val J = """
    ...###
    ....#.
    ....#.
    ....#.
    ....#.
    ....#.
    ....#.
    #...#.
    #...#.
    .###..""".trimIndent()

    private val N = """
    #....#
    ##...#
    ##...#
    #.#..#
    #.#..#
    #..#.#
    #..#.#
    #...##
    #...##
    #....#""".trimIndent()

    private val K = """
    #....#
    #...#.
    #..#..
    #.#...
    ##....
    ##....
    #.#...
    #..#..
    #...#.
    #....#""".trimIndent()

    private val B = """
    #####.
    #....#
    #....#
    #....#
    #####.
    #....#
    #....#
    #....#
    #....#
    #####.""".trimIndent()

    private val Z = """
    ######
    .....#
    .....#
    ....#.
    ...#..
    ..#...
    .#....
    #.....
    #.....
    ######""".trimIndent()

    private val E = """
    ######
    #.....
    #.....
    #.....
    #####.
    #.....
    #.....
    #.....
    #.....
    ######""".trimIndent()

    private val I = """
    ###
    .#.
    .#.
    .#.
    .#.
    .#.
    .#.
    .#.
    .#.
    ###""".trimIndent()

    private val map = mapOf(
            G to 'G',
            J to 'J',
            N to 'N',
            K to 'K',
            B to 'B',
            Z to 'Z',
            E to 'E',
            I to 'I'
    )

    fun ocr(input: String) = split(input).map { map[it] ?: '.' }.joinToString("")

    private fun split(input: String) : List<String> {
        val list = input.split('\n')
                .filterNot { it.trim().isEmpty() }
                .map { it.toCharArray() }
                .toList()

        if(list.size != 10) {
            throw IllegalArgumentException("Text should be 10 lines high")
        }

        val width = list.first().size
        val output = mutableListOf<String>()
        var c = 0
        while(c <  width) {
            if(columnEmpty(list, c)) {
                c++
                continue
            }
            var c2 = c + 1

            while(c2 < width && !columnEmpty(list, c2) ) {
                c2++
            }

            output += extract(list, c, c2)
            c = c2
        }

        return output
    }

    private fun columnEmpty(input: List<CharArray>, c: Int) =
        (0 until 10).none { r -> input[r][c] == '#' }

    private fun extract(input: List<CharArray>, c1: Int, c2: Int) : String {
        val builder = StringBuilder()

        for(r in 0 until 10) {
            for(c in c1 until c2) {
                builder.append(input[r][c])
            }
            builder.append('\n')
        }

        return builder.toString().trim()
    }
}
