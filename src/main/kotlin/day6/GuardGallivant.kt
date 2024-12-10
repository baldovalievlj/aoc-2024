package day6

import Utill.Direction
import Utill.Point
import Utill.getResourceFile

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
}

fun List<List<String>>.startingPoint(): Pair<Point, Direction> = this.indices.firstNotNullOf { x ->
    this[x].withIndex().firstNotNullOfOrNull { y ->
        Direction.from(y.value)
            ?.let { Point(x, y.index) to it }
    }
}

fun List<List<String>>.checkNextStep(point: Point) {
    this[point.x][point.y]

}

fun List<List<String>>.getNextPointOrNull(point: Point, direction: Direction): Point? =
    direction.getNextPointInRange(point, indices, this[0].indices)

fun List<List<String>>.getPointValue(point: Point): String = this[point.x][point.y]
fun Direction.rotate(): Direction = Direction.values().let { directions ->
    if (this.ordinal < directions.size - 1) {
        directions[this.ordinal + 1]
    } else {
        directions[0]
    }
}
