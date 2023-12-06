package puzzles

import println
import readInput

fun main() {
    part1(getData(readInput("day6"))).println()
    part2(readInput("day6")).println()
}

fun getData(input: List<String>): List<Pair<Int, Int>> {
    val times = input[0].replace("\\s{2,}".toRegex(), " ").replace("Time: ", "").split(" ")
    val distances = input[1].replace("\\s{2,}".toRegex(), " ").replace("Distance: ", "").split(" ")

    return times.zip(distances).map { it.first.toInt() to it.second.toInt() }
}

private fun part1(data: List<Pair<Int, Int>>): Int {
    var result = 1

    for (dataItem in data) {
        val recordDistance = dataItem.second
        var amountOfTimes = 0

        for (speed in 0..dataItem.first) {
            val distance = speed * (dataItem.first - speed)

            if (distance > recordDistance) {
                amountOfTimes++
            }
        }

        result *= amountOfTimes
    }

    return result
}


private fun part2(input: List<String>): Int {
    val time = input[0].replace("Time:", "").replace(" ", "").toLong()
    val recordDistance = input[1].replace("Distance:", "").replace(" ", "").toLong()

    var amountOfTimes = 0

    for (speed in 0..time) {
        val distance = speed * (time - speed)

        if (distance > recordDistance) {
            amountOfTimes++
        }
    }

    return amountOfTimes

}