package day3

import Utill.getResourceFile

fun main() {
    val text = getResourceFile("day3").readText()

    println(multiply(text))

    println(multiplyEnabled(text))
}

fun multiply(text: String) = extractMul(text).extractPairs().sumOf { (one, two) -> one * two }

fun multiplyEnabled(text: String) = extractMulWithEnable(text).extractEnabledPairs().sumOf { (one, two) -> one * two }

fun extractMul(text: String): List<String> = text.extractWithRegex("mul\\((\\d+),(\\d+)\\)")

fun extractMulWithEnable(text: String): List<String> =
    text.extractWithRegex("mul\\((\\d+),(\\d+)\\)|do\\(\\)|don't\\(\\)")

fun List<String>.extractPairs(): List<Pair<Int, Int>> = this.map { pair ->
    val (one, two) = pair.split(",").map { it.extractNumber()?.toInt() ?: 0 }
    one to two
}

fun List<String>.extractEnabledPairs(): List<Pair<Int, Int>> =
    this.fold(Pair(listOf<String>(), true)) { (list, enabled), element ->
        when (element) {
            "don't()" -> list to false
            "do()" -> list to true
            else -> {
                if (enabled) {
                    list + element to true
                } else {
                    list to false
                }
            }
        }
    }.first.extractPairs()

fun String.extractNumber(): String? = "(\\d+)".toRegex().find(this)?.value

fun String.extractWithRegex(regex: String): List<String> = regex.toRegex().findAll(this).map { it.value }.toList()