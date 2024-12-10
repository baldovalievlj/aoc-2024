package Utill

import day6.getNextPointOrNull

enum class Direction(val sign: String) {
    UP("^"),
    RIGHT(">"),
    DOWN("v"),
    LEFT("<");

    companion object {
        private val map = Direction.values().associateBy { it.sign }
        infix fun from(value: String): Direction? = map[value]
    }

    fun getNextPoint(point: Point): Point = when (this) {
        UP -> Point(point.x - 1, point.y)
        RIGHT -> Point(point.x, point.y + 1)
        DOWN -> Point(point.x + 1, point.y)
        LEFT -> Point(point.x, point.y - 1)
    }


    fun getNextPointInRange(point: Point, xRange: IntRange, yRange: IntRange): Point? =
        this.getNextPoint(point).takeIf {
            it.x in xRange && it.y in yRange
        }
}