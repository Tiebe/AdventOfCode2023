package puzzles

import println
import readInput
import kotlin.math.pow

fun main() {
    part1(readInput("day4")).println()
    part2(readInput("day4")).println()
}

private fun part1(input: List<String>): Int {
    var score = 0

    for (line in input) {
        val numbers = line.replace("\\s+".toRegex(), " ").split(": ")[1].split(" | ")

        val winning = numbers[0].split(" ").map { it.toInt() }
        val submitted = numbers[1].split(" ").map { it.toInt() }

        val submittedWinning = winning.filter { submitted.contains(it) }

        if (submittedWinning.isNotEmpty()) {
            score += 1 * (2.0.pow(submittedWinning.size - 1)).toInt()
        }
    }

    return score
}

private fun part2(input: List<String>): Int {
    // winning, submitted, copies
    val cards = mutableMapOf<Pair<List<Int>, List<Int>>, Int>()

    for (line in input) {
        val numbers = line.replace("\\s+".toRegex(), " ").split(": ")[1].split(" | ")

        val winning = numbers[0].split(" ").map { it.toInt() }
        val submitted = numbers[1].split(" ").map { it.toInt() }

        cards[winning to submitted] = 1
    }

    for (numbersPair in cards.keys) {
        val submittedWinning = numbersPair.first.filter { numbersPair.second.contains(it) }

        for (i in 1..submittedWinning.size) {
            if (cards.keys.indexOf(numbersPair) + 1 >= cards.size) {
                break
            }

            val value = cards.keys.toList()[cards.keys.indexOf(numbersPair) + i]
            cards[value] = cards[value]!! + cards[numbersPair]!!
        }
    }

    return cards.values.sum()
}