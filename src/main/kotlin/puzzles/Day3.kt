package puzzles

import println
import readInput
import kotlin.math.abs
import kotlin.math.log10

fun main() {
    part1(readInput("day3")).println()
    part2(readInput("day3")).println()
}

private fun part1(input: List<String>): Int {
    val schematic = input.map { line ->
        line.toList()
    }

    val partNumbers = mutableListOf<Int>()

    for (i in schematic.indices) {
        val numbers = getNumbers(schematic[i])

        // check if there are any adjacent "."s to the numbers
        for (number in numbers) {
            val minX = number.key.first - 1
            val maxX = number.key.last + 1

            val minY = i - 1
            val maxY = i + 1

            for (x in minX..maxX) {
                var found = false

                for (y in minY..maxY) {
                    if (x in schematic[0].indices && y in schematic.indices) {
                        if (schematic[y][x] != '.' && !schematic[y][x].isDigit()) {
                            partNumbers.add(number.value)
                            found = true
                            break
                        }
                    }
                }

                if (found) {
                    break
                }
            }
        }
    }

    return partNumbers.sum()
}

private fun part2(input: List<String>): Int {
    var sum = 0

    input.forEachIndexed { y, line ->
        line.forEachIndexed { x, char ->
            if (!char.isDigit() && char == '*') {
                val neighbors = findNeighborNumbers(input, Pair(x, y))
                val neighborsSet = HashMap<Pair<Int, Int>, Int>()

                neighbors.forEach {
                    val (position, value) = it
                    neighborsSet[position] = value
                }

                if (neighborsSet.size == 2) {
                    sum += neighborsSet.values.reduce { acc, i -> acc * i }
                }
            }
        }
    }

    return sum
}


fun isAdjacent(x: Int, y: Int, number: IntRange): Boolean {
    // check x,
    val minX = x - 1
    val maxX = x + 1

    val minY = y - 1
    val maxY = y + 1

    for (i in minX..maxX) {
        for (j in minY..maxY) {
            if (i in number && j in number) {
                return true
            }
        }
    }

    return false
}


fun findNeighborNumbers(input: List<String>,
                        position: Pair<Int, Int>
    // Return item is a List of Pairs.
    // The first item is the x,y cords of the number's starting digit
    // The second item is the number
): List<Pair<Pair<Int, Int>, Int>> {
    val (x, y) = position
    val foundNumbers = mutableListOf<Pair<Pair<Int, Int>, Int>> ()
    // Checking N
    try {
        foundNumbers.add(getNumberAtPosition(input, Pair(x, y-1)))
    } catch (_: Error) { }

    // Checking NE
    try {
        foundNumbers.add(getNumberAtPosition(input, Pair(x+1, y-1)))
    } catch (_: Error) { }

    // Checking E
    try {
        foundNumbers.add(getNumberAtPosition(input, Pair(x+1, y)))
    } catch (_: Error) { }

    // Checking SE
    try {
        foundNumbers.add(getNumberAtPosition(input, Pair(x+1, y+1)))
    } catch (_: Error) { }

    // Checking S
    try {
        foundNumbers.add(getNumberAtPosition(input, Pair(x, y+1)))
    } catch (_: Error) { }

    // Checking SW
    try {
        foundNumbers.add(getNumberAtPosition(input, Pair(x-1, y+1)))
    } catch (_: Error) { }

    // Checking W
    try {
        foundNumbers.add(getNumberAtPosition(input, Pair(x-1, y)))
    } catch (_: Error) { }

    // Checking NW
    try {
        foundNumbers.add(getNumberAtPosition(input, Pair(x-1, y-1)))
    } catch (_: Error) { }

    
    return foundNumbers
}

fun getNumberAtPosition(input: List<String>,
                        position: Pair<Int, Int>): Pair<Pair<Int, Int>, Int> {
    val (x, y) = position
        if (!input[y][x].isDigit()) {
        throw Error("Expected '${input[y][x]}' at $x, $y to be digit")
    }

    val digits = mutableListOf<Char>()

    var lowestX = x

        var nX = x
    while (nX > -1 && input[y][nX].isDigit()) {
                digits.add(0, input[y][nX])
        lowestX = nX
        nX -= 1
    }

        nX = x + 1
    while (nX < input[y].length && input[y][nX].isDigit()) {
                digits.add(input[y][nX])
        nX += 1
    }

    val n = digits.joinToString("").toInt()
        return Pair(Pair(lowestX, y), n)
}

fun getNumbers(line: List<Char>): MutableMap<IntRange, Int> {
    val regex = Regex("\\d+")

    val numbers = regex.findAll(line.joinToString("")).map { it.value.toInt() }.toList()
    val numberRanges = regex.findAll(line.joinToString("")).map { it.range }.toList()

    val numberMap = mutableMapOf<IntRange, Int>()

    for (i in numbers.indices) {
        numberMap[numberRanges[i]] = numbers[i]
    }

    return numberMap
}
