import java.io.File

fun getResourceFile(fileName: String): File = File(ClassLoader.getSystemResource("${fileName}.txt").file)
