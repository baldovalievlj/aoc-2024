package day5

import Utill.getResourceFile
import Utill.splitToInts
import Utill.swap

fun main() {
    val lines = getResourceFile("day5").readLines()
    val (rules, numbers) = partitionRulesNumbers(lines)
    val (right, wrong) = partitionRightWrong(rules, numbers)

    println(right.sumOfMiddle())

    println(fixWrong(wrong, rules).sumOfMiddle())

}

fun partitionRulesNumbers(lines: List<String>) = lines.filter { it.isNotEmpty() }
    .partition { it.contains("|") }
    .let { (rules, numbers) ->
        mapToRules(rules) to numbers.splitToInts(",")
    }

fun partitionRightWrong(
    rules: Map<Int, List<Int>>,
    lines: List<List<Int>>
): Pair<List<List<Int>>, List<List<Int>>> = lines.partition {
    it.fold(true) { acc, num ->
        acc && it.isCorrect(rules, num)
    }
}

fun List<Int>.isCorrect(rules: Map<Int, List<Int>>, number: Int): Boolean = this.indexOfOrNull(number)?.let { index ->
    findWrongIndexOrNull(rules, index) == null
} ?: true

fun fixWrong(lines: List<List<Int>>, rules: Map<Int, List<Int>>): List<List<Int>> = lines.map { numbers ->
    numbers.fixOrder(rules, 0)
}

tailrec fun List<Int>.fixOrder(rules: Map<Int, List<Int>>, currentIndex: Int): List<Int> =
    if (currentIndex == this.size) {
        this
    } else {
        val wrongIndex = findWrongIndexOrNull(rules, currentIndex)
        if (wrongIndex != null) {
            swap(wrongIndex, currentIndex).fixOrder(rules, wrongIndex)
        } else {
            fixOrder(rules, currentIndex + 1)
        }
    }

fun List<Int>.findWrongIndexOrNull(rules: Map<Int, List<Int>>, index: Int): Int? = this.slice(0..index)
    .indexOfFirst {
        rules[this[index]]?.contains(it) ?: true
    }.takeIf { it >= 0 }


fun mapToRules(lines: List<String>): Map<Int, List<Int>> = lines.map {
    val (one, two) = it.trim().split("|")
    one.toInt() to two.toInt()
}.groupBy({ it.first }) { it.second }

fun List<List<Int>>.sumOfMiddle() = this.sumOf { it[it.size / 2] }

fun <T> List<T>.indexOfOrNull(element: T) = this.indexOf(element).takeIf { it >= 0 }