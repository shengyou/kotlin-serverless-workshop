import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun main() {
    val dateString = "17 Mar 2021 11:37:31"
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss")

    val dateTime = LocalDateTime.parse(dateString, formatter)
    println(dateTime)
}
