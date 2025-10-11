package de.honoka.lavender.lavsource.starter.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ServerProperties(private val mainProperties: MainProperties) {

    @Value($$"${server.port:8080}")
    var serverPort: Int? = null

    @Value($$"${server.servlet.context-path:}")
    var serverContextPath: String? = null

    val serverAccessUrlPrefix: String
        get() = "http://${mainProperties.remoteAccessHostName ?: "localhost"}:$serverPort".let {
            if(serverContextPath?.isBlank() == false) "$it/$serverContextPath" else it
        }
}
