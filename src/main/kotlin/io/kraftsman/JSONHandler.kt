package io.kraftsman

import com.google.cloud.functions.HttpFunction
import com.google.cloud.functions.HttpRequest
import com.google.cloud.functions.HttpResponse
import java.net.HttpURLConnection

class JSONHandler: HttpFunction {
    override fun service(request: HttpRequest, response: HttpResponse) {
        with(response) {
            setStatusCode(HttpURLConnection.HTTP_OK)
            setContentType("application/json")
            //language=JSON
            writer.write("{\n  \"data\": [\n    {\n      \"id\": 1,\n      \"title\": \"RSS news title\",\n      \"content\": \"Some content\",\n      \"publishedAt\": \"2021-05-24 00:00:00\"\n    }\n  ]\n}")
        }
    }
}
