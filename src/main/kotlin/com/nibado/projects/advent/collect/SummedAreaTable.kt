package com.nibado.projects.advent.collect

object SummedAreaTable {
    /**
     * Get the summed area table for given matrix
     * O(MN) time, O(MN) space
     * @param matrix    source matrix
     * @return        summed area table
     */
    fun getSummedAreaTable(matrix: Array<IntArray>): Array<IntArray> {
        val rowSize = matrix.size
        val colSize = matrix[0].size
        val cumulativeMatrix = Array(rowSize) { IntArray(colSize) }
        for (i in 0 until rowSize) {
            for (j in 0 until colSize) {
                cumulativeMatrix[i][j] = getSubMatrixSum(i, j, matrix)
            }
        }
        return cumulativeMatrix
    }

    /**
     * Get the sum of all cells in sub-matrix defined by bottom right coordinates
     * @param row        row number for bottom right coordinate
     * @param col        column number for bottom right coordinate
     * @param matrix    parent matrix
     * @return        sub-matrix sum
     */
    private fun getSubMatrixSum(row: Int, col: Int, matrix: Array<IntArray>): Int {
        var sum = 0
        for (i in 0..row) {
            for (j in 0..col) {
                sum += matrix[i][j]
            }
        }
        return sum
    }

    /**
     * Helper method for processSummedAreaTable
     * @param row        current row number
     * @param col        current column number
     * @param matrix    source matrix
     * @return        sub-matrix sum
     */
    fun getVal(row: Int, col: Int, matrix: Array<IntArray>): Int {
        val leftSum: Int                    // sub matrix sum of left matrix
        val topSum: Int                        // sub matrix sum of top matrix
        val topLeftSum: Int                    // sub matrix sum of top left matrix
        val curr = matrix[row][col]    // current cell value
        /* top left value is itself */
        if (row == 0 && col == 0) {
            return curr
        } else if (row == 0 && col != 0) {
            leftSum = matrix[row][col - 1]
            return curr + leftSum
        }/* top row */
        /* left-most column */
        if (row != 0 && col == 0) {
            topSum = matrix[row - 1][col]
            return curr + topSum
        } else {
            leftSum = matrix[row][col - 1]
            topSum = matrix[row - 1][col]
            topLeftSum = matrix[row - 1][col - 1] // overlap between leftSum and topSum
            return curr + leftSum + topSum - topLeftSum
        }
    }

    /**
     * Prints matrix row-wise
     * @param matrix
     */
    fun printMatrix(matrix: Array<IntArray>) {
        val rowSize = matrix.size
        val colSize = matrix[0].size
        for (i in 0 until rowSize) {
            for (j in 0 until colSize) {
                print(matrix[i][j].toString() + " ")
            }
            println()
        }
    }



}

fun main(args: Array<String>) {
    val image = arrayOf(intArrayOf(3, 1, 2, 1), intArrayOf(2, 1, 1, 1), intArrayOf(3, 1, 2, 1))
    println("Non-in-place implementation:")
    SummedAreaTable.printMatrix(SummedAreaTable.getSummedAreaTable(image))
}
