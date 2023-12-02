package puzzles

import println
import readInput

fun main() {
    part1(readInput("day2")).println()
    part2(readInput("day2")).println()
}


private fun part1(input: List<String>): Int {
    val games = getGames(input)

    val maxRed = 12
    val maxGreen = 13
    val maxBlue = 14

    val result = games.map { game ->
        if (game.value.any { it[RGB.RED]!! > maxRed || it[RGB.GREEN]!! > maxGreen || it[RGB.BLUE]!! > maxBlue }) {
            return@map 0
        }
        game.key
    }.sum()

    return result
}

private fun part2(input: List<String>): Int {
    val games = getGames(input)

    val result = games.map { game ->
        val maxRed = game.value.maxBy { it[RGB.RED]!! }[RGB.RED]!!
        val maxGreen = game.value.maxBy { it[RGB.GREEN]!! }[RGB.GREEN]!!
        val maxBlue = game.value.maxBy { it[RGB.BLUE]!! }[RGB.BLUE]!!

        maxRed * maxGreen * maxBlue
    }.sum()

    return result
}

private fun getGames(input: List<String>): Map<Int, List<Map<RGB, Int>>> {
    return input.associate { line ->
        val id = line.substring(5, line.indexOf(':')).toInt()

        val rounds = line.substring(line.indexOf(':') + 2).split("; ").map {
            getRGB(it)
        }

        id to rounds
    }
}

private fun getRGB(input: String): Map<RGB, Int> {
    val values = input.split(", ")

    val colors = values.associate {
        if (it.endsWith("red")) {
            RGB.RED to it.substring(0, it.indexOf(' ')).toInt()
        } else if (it.endsWith("green")) {
            RGB.GREEN to it.substring(0, it.indexOf(' ')).toInt()
        } else {
            RGB.BLUE to it.substring(0, it.indexOf(' ')).toInt()
        }
    }.toMutableMap()

    RGB.entries.forEach { color ->
        if (!colors.any { it.key == color }) {
            colors[color] = 0
        }
    }

    return colors.toMap()
}

enum class RGB {
    RED, GREEN, BLUE
}