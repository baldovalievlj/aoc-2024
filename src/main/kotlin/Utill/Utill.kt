package Utill

import java.io.File

fun getResourceFile(fileName: String): File = File(ClassLoader.getSystemResource("${fileName}.txt").file)

fun List<String>.splitToInts(delimiter: String = " "): List<List<Int>> = this.map { it.splitToInts(delimiter) }

fun String.splitToInts(delimiter: String = " "): List<Int> = this.split(delimiter).map { it.trim().toInt() }

fun String.splitToLongs(delimiter: String = " "): List<Long> = this.split(delimiter).map { it.trim().toLong() }

fun <T> List<T>.swap(index: Int, toIndex: Int):List<T> = this.mapIndexed { i, it ->
    when (i) {
        index -> this[toIndex]
        toIndex -> this[index]
        else -> it
    }
}