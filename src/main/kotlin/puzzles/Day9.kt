package puzzles

import println
import readInput

fun main() {
    part1(readInput("day9")).println()
    part2(readInput("day9")).println()
}

fun List<Int>.extrapolate(): Int = if (all { it == 0 }) 0 else {
    last() + windowed(2) { it.last() - it.first() }.extrapolate()
}

private fun part1(input: List<String>): Int {
    val lines = input.map { it.split(" ").map { it.toInt() } }

    return lines.sumOf { it.extrapolate() }
}


private fun part2(input: List<String>): Int {
    val lines = input.map { it.split(" ").map { it.toInt() } }

    return lines.sumOf { it.reversed().extrapolate() }
}