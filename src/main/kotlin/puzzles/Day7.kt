package puzzles

import println
import readInput

fun main() {
    part1(readInput("day7")).println()
    part2(readInput("day7")).println()
}


private fun part1(input: List<String>): Int {
    val cards = input.map {
        val card = it.split(" ")
        Hand(card[0].toCharArray().toList(), card[1])
    }.sortedDescending()

    cards.forEach {
        println(it)
        println(it.getType())
        println(it.getGroup())
    }

    return cards.mapIndexed { index, hand -> hand.bid * (index+1) }.sum()
}

private fun part2(input: List<String>): Int {

    return 0
}


data class Hand(private val cards: List<Int>, val bid: Int): Comparable<Hand> {
    constructor(cards: List<Char>, bid: String) : this(
        cards.map { cardValue(it) },
        bid.toInt()
    )

    override fun compareTo(other: Hand): Int {
        val type = getType()
        val otherType = other.getType()

        if (type != otherType) {
            return type.compareTo(otherType)
        }

        for (i in cards.indices) {
            if (cards[i] != other.cards[i]) {
                return other.cards[i] - cards[i]
            }
        }

        return 0
    }

    fun getGroup(): Map<Int, List<Int>> {
        val destination = LinkedHashMap<Int, MutableList<Int>>()

        cards.forEachIndexed { index, card ->
            if (card == 1 && index > 0) {
                val key = cards[index - 1]
                val list = destination.getOrPut(key) { ArrayList() }
                list.add(card)
                return@forEachIndexed
            }

            val key = card
            val list = destination.getOrPut(key) { ArrayList() }
            list.add(card)

            if (index > 1 && cards[index - 1] == 1 && cards[index - 2] != key) {
                list.add(1)
            }
        }
        return destination
    }

    fun getType(): HandType {
        val grouped = getGroup()

        val counts = grouped.map { it.value.size }.sortedDescending()

        println(counts)

        return when (true) {
            counts.contains(5) -> HandType.FiveOfAKind
            (counts.contains(4)) -> HandType.FourOfAKind
            (counts.contains(3) && counts.any { it >= 2 }) -> HandType.FullHouse
            counts.contains(3) -> HandType.ThreeOfAKind
            (counts.filter { it == 2 }.size == 2) -> HandType.TwoPair
            counts.contains(2) -> HandType.OnePair
            else -> HandType.HighCard
        }
    }
}


enum class HandType {
    FiveOfAKind,
    FourOfAKind,
    FullHouse,
    ThreeOfAKind,
    TwoPair,
    OnePair,
    HighCard
}


fun cardValue(card: Char): Int {
    return when (card) {
        'J' -> 1 // joker
        'A' -> 14
        'K' -> 13
        'Q' -> 12
        'T' -> 10
        else -> card.digitToInt()
    }
}