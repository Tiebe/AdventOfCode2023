package puzzles

import println
import readInput

fun main() {
    part1(readInput("day1")).println()
    part2(readInput("day1")).println()
}

private fun part1(input: List<String>): Int {
    val numbers = input.map { line ->
        val numbers = line.filter { it.isDigit() }

        numbers.first().digitToInt() * 10 + numbers.last().digitToInt()
    }

    return numbers.sum()
}

private fun part2(input: List<String>): Int {
    val numberStrings = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "1", "2", "3", "4", "5", "6", "7", "8", "9")

    val numbers = input.map { line ->
        val foundFirstNumbers = numberStrings.map { it to line.indexOf(it) }.filter { it.second != -1 }
        val firstNumber = numberStrings.indexOf(foundFirstNumbers.minBy { it.second }.first) % 9 + 1

        val foundLastNumbers = numberStrings.map { it to line.lastIndexOf(it) }.filter { it.second != -1 }
        val lastNumber = numberStrings.indexOf(foundLastNumbers.maxBy { it.second }.first) % 9 + 1

        firstNumber * 10 + lastNumber
    }

    return numbers.sum()
}