package io.kraftsman.handlers

import com.github.javafaker.Faker
import com.google.cloud.functions.HttpFunction
import com.google.cloud.functions.HttpRequest
import com.google.cloud.functions.HttpResponse
import io.kraftsman.dtos.News
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException
import java.net.HttpURLConnection
import java.util.*
import kotlin.random.Random
import kotlin.time.ExperimentalTime
import kotlin.time.days

class JSONHandler : HttpFunction {

    @ExperimentalTime
    @Throws(IOException::class)
    override fun service(request: HttpRequest, response: HttpResponse) {

        val param = request.getFirstQueryParameter("limit").orElse("")
        val limit = param.toIntOrNull() ?: 10

        val faker = Faker.instance(Locale.TRADITIONAL_CHINESE)
        val current = Clock.System.now()
        val timeZone = TimeZone.of("Asia/Taipei")

        val news = (1..limit).map { id ->
            News(
                id = id,
                title = faker.lorem().sentence(Random.nextInt(7, 15)),
                author = faker.name().fullName(),
                content = faker.lorem().paragraph(Random.nextInt(3, 10)),
                permalink = "https://${faker.internet().url()}/posts/${Random.nextInt(1, 100)}",
                publishedAt = (current - (limit - id).days).toLocalDateTime(timeZone)
            )
        }

        with(response) {
            setStatusCode(HttpURLConnection.HTTP_OK)
            setContentType("application/json")
            writer.write(
                Json.encodeToString(
                    mapOf(
                        "data" to news
                    )
                )
            )
        }
    }
}
