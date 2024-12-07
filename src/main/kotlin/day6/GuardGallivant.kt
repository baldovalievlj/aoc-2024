package day6

import getResourceFile

fun main() {
    val matrix = getResourceFile("day6").readLines().map { it.chunked(1) }

    println(findDistinctPositions(matrix))

    println(findStuckPositions(matrix))
}

fun findDistinctPositions(matrix: List<List<String>>): Int = matrix.getPath().distinct().count()

//TODO Second part
fun findStuckPositions(matrix: List<List<String>>): Int {
    val paths = matrix.getPath().toList()
    val filtered = paths.windowed(2).filter { (one, two) ->
        one != two
    }.map { (one, two) -> one }

    return filtered.groupBy { it }.filter { it.value.size > 1 }.keys.also { println(it) }.count()
}

fun List<List<String>>.getPath(): Sequence<Point> = generateSequence(this.startingPoint()) { (point, direction) ->
    this.getNextPointOrNull(point, direction)?.let { nextPoint ->
        if (this.getPointValue(nextPoint) == "#") {
            point to direction.rotate()
        } else {
            nextPoint to direction
        }
    }
}.map {
    it.first
}.also {
    it.forEach { point ->
        println(point)
    }
}

fun List<List<String>>.startingPoint(): Pair<Point, Direction> = this.indices.firstNotNullOf { x ->
    this[x].withIndex().firstNotNullOfOrNull { y ->
        Direction.from(y.value)
            ?.let { (x to y.index) to it }
    }
}

fun List<List<String>>.checkNextStep(point: Point) {
    this[point.first][point.second]

}

fun List<List<String>>.getNextPointOrNull(point: Point, direction: Direction): Point? =
    direction.getNextPoint(point).takeIf {
        it.first in indices && it.second in this[it.first].indices
    }

fun Direction.getNextPoint(point: Point): Point = when (this) {
    Direction.UP -> (point.first - 1 to point.second)
    Direction.RIGHT -> (point.first to point.second + 1)
    Direction.DOWN -> (point.first + 1 to point.second)
    Direction.LEFT -> (point.first to point.second - 1)
}

fun List<List<String>>.getPointValue(point: Point): String = this[point.first][point.second]
fun Direction.rotate(): Direction = Direction.values().let { directions ->
    if (this.ordinal < directions.size - 1) {
        directions[this.ordinal + 1]
    } else {
        directions[0]
    }
}

enum class Direction(val sign: String) {
    UP("^"),
    RIGHT(">"),
    DOWN("v"),
    LEFT("<");

    companion object {
        private val map = Direction.values().associateBy { it.sign }
        infix fun from(value: String): Direction? = map[value]
    }
}

typealias Point = Pair<Int, Int>