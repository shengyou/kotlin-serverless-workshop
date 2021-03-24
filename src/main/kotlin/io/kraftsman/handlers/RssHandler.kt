package io.kraftsman.handlers

import com.google.cloud.functions.HttpFunction
import com.google.cloud.functions.HttpRequest
import com.google.cloud.functions.HttpResponse
import io.kraftsman.dtos.News
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import tw.ktrssreader.generated.CustomChannelParser
import java.io.IOException
import java.net.HttpURLConnection
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime as JavaLocalDateTime

class RssHandler: HttpFunction {

    private val rssSource = listOf(
        "https://blog.jetbrains.com/kotlin/feed/",
        "https://andyludeveloper.medium.com/feed",
        "https://drawson.medium.com/feed",
    )

    @Throws(IOException::class)
    override fun service(request: HttpRequest, response: HttpResponse) {

        val client = OkHttpClient()
        val news = mutableListOf<News>()
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss")

        rssSource.forEach { url ->
            val rssRequest = Request.Builder().url(url).build()
            val xmlString = client.newCall(rssRequest).execute().body?.string() ?: ""
            val rssChannel = CustomChannelParser.parse(xmlString)
            rssChannel.items.forEach { item ->
                val parsedPubDate = item.pubDate?.substringAfter(", ")
                    ?.replace(" +0000", "")
                    ?.replace(" GMT", "")
                    ?.trim()
                    .toString()
                val javaLocalDateTime = JavaLocalDateTime.parse(parsedPubDate, formatter)

                news.add(
                    News(
                        id = null,
                        title = item.title ?: "",
                        author = item.author ?: "",
                        content = item.description ?: "",
                        permalink = item.link ?: "",
                        publishedAt = LocalDateTime(
                            javaLocalDateTime.year,
                            javaLocalDateTime.month,
                            javaLocalDateTime.dayOfMonth,
                            javaLocalDateTime.hour,
                            javaLocalDateTime.minute,
                            javaLocalDateTime.second,
                            javaLocalDateTime.nano
                        )
                    )
                )
            }
        }

        with(response) {
            setStatusCode(HttpURLConnection.HTTP_OK)
            setContentType("application/json")
            writer.write(
                Json.encodeToString(
                    mapOf(
                        "data" to news.sortedByDescending { it.publishedAt }.mapIndexed { index, news -> news.copy(id = index + 1) }
                    )
                )
            )
        }
    }
}