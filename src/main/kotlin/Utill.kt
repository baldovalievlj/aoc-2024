import java.io.File

fun getResourceFile(fileName: String): File = File(ClassLoader.getSystemResource("${fileName}.txt").file)

fun List<String>.splitToInts(delimiter: String = " "): List<List<Int>> = this.map { it.split(delimiter).map { number -> number.toInt() } }