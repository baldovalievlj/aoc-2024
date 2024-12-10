package day7

import Utill.getResourceFile
import Utill.splitToLongs

fun main() {
    val lines = getResourceFile("day7").readLines()

    println(mapLines(lines).getSumsOfEquation())

    println(mapLines(lines).getSumsOfEquationWithConcatenation())
}

fun Map<Long, List<Long>>.getSumsOfEquation(): Long = this.getSumsPossibleWith {
    findCombinations(0, 0)
}

fun Map<Long, List<Long>>.getSumsOfEquationWithConcatenation(): Long = this.getSumsPossibleWith {
    findCombinationsWithConcatenation(0, 0)
}

fun Map<Long, List<Long>>.getSumsPossibleWith(block: List<Long>.() -> List<Long>): Long =
    this.keys.fold(0) { acc, key ->
        this[key]?.let { values ->
            values.block().firstOrNull { it == key }
        }?.let { acc + it } ?: acc
    }

tailrec fun List<Long>.findCombinations(currentIndex: Int, value: Long): List<Long> {
    val current = this[currentIndex]
    val multiplier = value.takeIf { it != 0L } ?: 1
    if (currentIndex == this.size - 1) {
        return listOf(current + value, current * multiplier)
    }
    return this.findCombinations(currentIndex + 1, current + value) +
            this.findCombinations(currentIndex + 1, current * multiplier)
}

tailrec fun List<Long>.findCombinationsWithConcatenation(currentIndex: Int, value: Long): List<Long> {
    val current = this[currentIndex]
    val multiplier = value.takeIf { it != 0L } ?: 1
    if (currentIndex == this.size - 1) {
        return listOf(current + value, current * multiplier, "${value}${current}".toLong())
    }
    return this.findCombinationsWithConcatenation(currentIndex + 1, current + value) +
            this.findCombinationsWithConcatenation(currentIndex + 1, current * multiplier) +
            this.findCombinationsWithConcatenation(currentIndex + 1, "${value}${current}".toLong())
}


fun mapLines(lines: List<String>): Map<Long, List<Long>> = lines.map {
    val (number, numbers) = it.split(": ")
    number.toLong() to numbers.splitToLongs()
}.toMap()