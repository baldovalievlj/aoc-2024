package day1

import Utill.getResourceFile
import kotlin.math.abs

fun main() {
    val lines = getResourceFile("day1").readLines()

    println(sumOfDistances(lines))

    println(similarityScore(lines))
}

fun sumOfDistances(lines: List<String>): Int = lines.splitToListsOfInts().let { (first, second) ->
    first.sorted().zip(second.sorted()) { one, two ->
        abs(one - two)
    }.sum()
}

fun similarityScore(lines: List<String>): Int = lines.splitToListsOfInts().let { (first, second) ->
    first.fold(0) { acc, i ->
        acc + (i * second.count { it == i })
    }
}

fun List<String>.splitToListsOfInts(): Pair<List<Int>, List<Int>> = this.map {
    val (one, two) = it.split("\\s+".toRegex())
    one.toInt() to two.toInt()
}.unzip()

