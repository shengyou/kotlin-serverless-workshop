import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun main() {
    val dateString = "17 Mar 2021 11:37:31"
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss", Locale.ENGLISH)

    val dateTime = LocalDateTime.parse(dateString, formatter)
    println(dateTime)
}
