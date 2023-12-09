package puzzles

import println
import readInput

fun main() {
    part1(readInput("day8")).println()
    part2(readInput("day8")).println()
}


private fun part1(input: List<String>): Int {
    val instructions = input[0].toCharArray()

    val navigation = input.subList(2, input.size).associate {
        val source = it.split(" = ")[0]
        val destinations = it.split(" = ")[1].replace("[()]".toRegex(), "").split(", ")

        source to destinations
    }

    var currentLocation = "AAA"
    var steps = 0

    while (currentLocation != "ZZZ") {
        for (instruction in instructions) {
            steps++

            if (instruction == 'L') {
                currentLocation = navigation[currentLocation]!![0]
            } else if (instruction == 'R') {
                currentLocation = navigation[currentLocation]!![1]
            }
        }
    }


    return steps
}


private fun part2(input: List<String>): Long {
    val steps = input.first()
    val map = input.drop(2).associate { line ->
        val (from, left, right) = """([A-Z]{3}) = \(([A-Z]{3}), ([A-Z]{3})\)""".toRegex().matchEntire(line)!!.groupValues.drop(1)
        from to listOf(left, right)
    }
    val counts = map.keys.filter { it.endsWith("A") }.map { startingPoint ->
        var current = startingPoint
        var count = 0L
        while (!current.endsWith("Z")) {
            steps.forEach { current = if (it == 'R') map[current]!![1] else map[current]!![0] }
            count += steps.length
        }
        count
    }
    return counts.reduce { acc, i -> findLCM(acc, i) }
}

fun findLCM(a: Long, b: Long): Long {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm % a == 0L && lcm % b == 0L) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}