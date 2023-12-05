package puzzles

import println
import readInput

fun main() {
    part1(readInput("day5")).println()
    part2(readInput("day5")).println()
}

private fun part1(input: List<String>): Long {
    val maps = input.flatMapIndexed { index, x ->
        when {
            index == 0 || index == input.lastIndex -> listOf(index)
            x.isEmpty() -> listOf(index - 1, index + 1)
            else -> emptyList()
        }
    }.windowed(size = 2, step = 2) { (from, to) -> input.slice(from..to) }.map { it.toMutableList().apply { removeFirst() } }.toMutableList().apply { removeFirst() }

    val seeds = input[0].split(" ").toMutableList().apply { removeFirst() }.map { it.toLong() }

    val seedToSoilMap = maps[0].toRangedMap()
    val soilToFertilizerMap = maps[1].toRangedMap()
    val fertilizerToWaterMap = maps[2].toRangedMap()
    val waterToLightMap = maps[3].toRangedMap()
    val lightToTemperatureMap = maps[4].toRangedMap()
    val temperatureToHumidityMap = maps[5].toRangedMap()
    val humidityToLocationMap = maps[6].toRangedMap()

    return seeds.minOf {
        val soil = getMappedLong(it, seedToSoilMap)
        val fertilizer = getMappedLong(soil, soilToFertilizerMap)
        val water = getMappedLong(fertilizer, fertilizerToWaterMap)
        val light = getMappedLong(water, waterToLightMap)
        val temperature = getMappedLong(light, lightToTemperatureMap)
        val humidity = getMappedLong(temperature, temperatureToHumidityMap)
        val location = getMappedLong(humidity, humidityToLocationMap)

        location
    }
}

private fun part2(input: List<String>): Long {
    val maps = input.flatMapIndexed { index, x ->
        when {
            index == 0 || index == input.lastIndex -> listOf(index)
            x.isEmpty() -> listOf(index - 1, index + 1)
            else -> emptyList()
        }
    }.windowed(size = 2, step = 2) { (from, to) -> input.slice(from..to) }.map { it.toMutableList().apply { removeFirst() } }.toMutableList().apply { removeFirst() }

    val seeds = input[0].split(" ").toMutableList().apply { removeFirst() }.map { it.toLong() }.chunked(2).map { it[0]..<it[0]+it[1] }

    val seedToSoilMap = maps[0].toRangedMap()
    val soilToFertilizerMap = maps[1].toRangedMap()
    val fertilizerToWaterMap = maps[2].toRangedMap()
    val waterToLightMap = maps[3].toRangedMap()
    val lightToTemperatureMap = maps[4].toRangedMap()
    val temperatureToHumidityMap = maps[5].toRangedMap()
    val humidityToLocationMap = maps[6].toRangedMap()

    var minLocation = Long.MAX_VALUE

    for (seedRange in seeds) {

        println(seedRange)

        for (seed in seedRange) {
            val soil = getMappedLong(seed, seedToSoilMap)
            val fertilizer = getMappedLong(soil, soilToFertilizerMap)
            val water = getMappedLong(fertilizer, fertilizerToWaterMap)
            val light = getMappedLong(water, waterToLightMap)
            val temperature = getMappedLong(light, lightToTemperatureMap)
            val humidity = getMappedLong(temperature, temperatureToHumidityMap)
            val location = getMappedLong(humidity, humidityToLocationMap)

            if (location < minLocation) {
                minLocation = location
            }
        }
    }

    return minLocation
}

fun getMappedLong(input: Long, map: List<Pair<LongRange, LongRange>>): Long {
    var mapped = input

    if (map.any { it.first.contains(input) }) {
        val mappedMap = map.first { it.first.contains(input) }
        mapped = input - mappedMap.first.first + mappedMap.second.first
    }

    return mapped
}


fun List<String>.toRangedMap(): List<Pair<LongRange, LongRange>> {
    return this.map {
        val items = it.split(" ")
        val rangeSize = items[2].toLong()

        items[1].toLong()..<items[1].toLong() + rangeSize to items[0].toLong()..<items[0].toLong() + rangeSize
    }
}