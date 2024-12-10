package day4

import Utill.Point
import Utill.getResourceFile

fun main() {
    val matrix: List<List<String>> = getResourceFile("day4").readLines().map { it.chunked(1) }

    println(getNumberOfXmas(matrix))

    println(getNumberOfMas(matrix))
}

fun getNumberOfXmas(matrix: List<List<String>>) = matrix.indices.flatMap { x: Int ->
    matrix[x].flatMapIndexed { y, current ->
        if (current == "X") {
            getRanges(x, y).map {
                matrix.isXmasInRange(it)
            }
        } else {
            listOf(false)
        }
    }
}.count { it }


fun getNumberOfMas(matrix: List<List<String>>) = matrix.indices.flatMap { x: Int ->
    matrix[x].mapIndexed { y, string ->
        if (string == "A") {
            matrix.isMasInPoints(getDiagonalPoints(x, y)) && matrix.isMasInPoints(getAntiDiagonalPoints(x, y))
        } else {
            false
        }
    }
}.count { it }

private fun List<List<String>>.isXmasInRange(range: Range): Boolean =
    range.first.zip(range.second).mapNotNull { (x, y) -> this.getIfSafe(Point(x, y)) }.joinToString("").isXmas()

fun List<List<String>>.isMasInPoints(points: Pair<Point, Point>): Boolean =
    points.let { (one, two) -> getIfSafe(one) to getIfSafe(two) }.isMas()

fun List<List<String>>.getIfSafe(point: Point): String? =
    if (point.isInRange(this.indices, this[point.x].indices)) this[point.x][point.y] else null

fun getRanges(x: Int, y: Int): List<Range> = listOf(
    x.sameRange() to y.forwardRange(),
    x.sameRange() to y.backwardRange(),
    x.forwardRange() to y.sameRange(),
    x.forwardRange() to y.backwardRange(),
    x.forwardRange() to y.forwardRange(),
    x.backwardRange() to y.sameRange(),
    x.backwardRange() to y.forwardRange(),
    x.backwardRange() to y.backwardRange(),
)

fun getDiagonalPoints(x: Int, y: Int): Pair<Point, Point> = Point(x - 1, y - 1) to Point(x + 1, y + 1)

fun getAntiDiagonalPoints(x: Int, y: Int): Pair<Point, Point> = Point(x - 1, y + 1) to Point(x + 1, y - 1)

fun Int.forwardRange(): List<Int> = (this..this + 3).toList()

fun Int.backwardRange(): List<Int> = (this downTo this - 3).toList()

fun Int.sameRange(): List<Int> = List(4) { this }

fun String.isXmas() = this == "XMAS"

fun Pair<String?, String?>.isMas(): Boolean = this == Pair("S", "M") || this == Pair("M", "S")

typealias Range = Pair<List<Int>, List<Int>>