package Utill

data class Point(val x: Int, val y: Int) {
    fun isInRange(xRange: IntRange, yRange: IntRange): Boolean = this.x in xRange && this.y in yRange

}
