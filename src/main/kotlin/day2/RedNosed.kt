package day2

import getResourceFile

fun main() {
    val lines = getResourceFile("day2").readLines().splitToInts()

    println(safeCount(lines))

    println(safeCountWithDampener(lines))
}


fun safeCount(lines: List<List<Int>>) = lines.count { line ->
    isSafe(line)
}

fun safeCountWithDampener(lines: List<List<Int>>) = lines.count { line ->
    isSafe(line) || line.indices.fold(false) { acc, i ->
        acc || isSafe(line.subList(0, i) + line.subList(i + 1, line.size))
    }
}

fun isSafe(line: List<Int>): Boolean = line.windowed(2).let { pairs ->
    val increasing = pairs.all { (one, two) -> increasing(one, two) }
    val decreasing = pairs.all { (one, two) -> decreasing(one, two) }
    return increasing || decreasing
}

fun increasing(one: Int, two: Int): Boolean = (one - two) in -1 downTo -3

fun decreasing(one: Int, two: Int): Boolean = (one - two) in 1..3

fun List<String>.splitToInts(): List<List<Int>> = this.map { it.split(" ").map { number -> number.toInt() } }
