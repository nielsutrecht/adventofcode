package com.nibado.projects.advent.collect

fun createSumMatrix(matrix: Array<IntArray>, sum: Array<IntArray>) {
    for (i in matrix.indices) {
        for (j in 0 until matrix[i].size) {
            sum[i][j] = matrix[i][j]
            if (i > 0)
                sum[i][j] += sum[i - 1][j]
            if (j > 0) {
                sum[i][j] += sum[i][j - 1]
                if (i > 0)
                    sum[i][j] -= sum[i - 1][j - 1] // because this is added twice in above two additions
            }
        }
    }
}

fun testSum() {
    val matrix = arrayOf(
            intArrayOf(1, 2, 4),
            intArrayOf(7, 4, 3),
            intArrayOf(2, 6, 5))

    val sum = SubMatrixSum(matrix)

    for (y in matrix.indices) {
        for (x in 0 until matrix[y].size) {
            print("${sum.get(x, y)}, ")
        }
        println()
    }

    println(sum.get(0, 0, 1, 1))
}
/*
1, 3, 7,
8, 14, 21,
10, 22, 34,
 */
class SubMatrixSum(private val matrix: Array<IntArray>) {
    private val sum = createSumMatrix()

    private fun createSumMatrix() : Array<IntArray> {
        val sum = Array(matrix.size) { IntArray(matrix[0].size) }

        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size) {
                sum[i][j] = matrix[i][j]
                if (i > 0)
                    sum[i][j] += sum[i - 1][j]
                if (j > 0) {
                    sum[i][j] += sum[i][j - 1]
                    if (i > 0)
                        sum[i][j] -= sum[i - 1][j - 1] // because this is added twice in above two additions
                }
            }
        }

        return sum
    }

    fun get(x: Int, y: Int) = sum[y][x]
    fun get(x1: Int, y1: Int, x2: Int, y2: Int) = sum[x2][y2] + sum[x1][y1 - 1] - sum[x1 - 1][y2] - sum[x2 - 1][y1 - 1]
}

fun main(args: Array<String>) {
    testSum()
}
