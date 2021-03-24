package io.kraftsman

import com.google.cloud.functions.HttpFunction
import com.google.cloud.functions.HttpRequest
import com.google.cloud.functions.HttpResponse
import java.net.HttpURLConnection

class PlainTextHandler: HttpFunction {
    override fun service(request: HttpRequest, response: HttpResponse) {
        with(response) {
            setStatusCode(HttpURLConnection.HTTP_OK)
            setContentType("text/plain")
            writer.write("Hello, Cloud Functions")
        }
    }
}
