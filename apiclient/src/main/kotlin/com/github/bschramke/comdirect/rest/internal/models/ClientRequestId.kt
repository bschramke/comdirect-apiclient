package com.github.bschramke.comdirect.rest.internal.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.Instant
import java.util.*

/**
 * Clients must provide a Client-Request-ID in the "x-http-request-info" HTTP header.
 * The Client-Request-ID consist of two parts, the [sessionId] and the [requestId].
 *
 * The [sessionId] represents a user session. The client is responsible for
 * creating a session ID before the first request and send it in all subsequent requests.
 * The definition of a user session depends on the type of the client. A client in the form
 * of a mobile app starts the user session when the app starts and ends it by closing the app.
 *
 * The [requestId] makes the request unique within the user session and
 * can e.g. derived from a timestamp in the format HHmmssSSS.
 *
 * @property sessionId representing a user session
 * @property requestId unique identifier of a request in the user session
 */
@Serializable
internal data class ClientRequestId(
  @SerialName("sessionId")
  val sessionId: String,
  @SerialName("requestId")
  val requestId: String
)

@Serializable
internal data class XHttpRequestInfo(
  @SerialName("clientRequestId")
  val clientRequestId: ClientRequestId
) {
  fun toJsonString() = Json.encodeToString(this)

  companion object {
    fun fromJsonString(value:String) = Json.decodeFromString<XHttpRequestInfo>(value)
  }
}

internal fun createXHttpRequestInfo(sessionId: String) = XHttpRequestInfo(createClientRequestId(sessionId))

private fun createClientRequestId(sessionId: String) = ClientRequestId(
  sessionId = sessionId,
  requestId = createReuestIdFromUnixEpoche()
)

// Request ID aus den letzten 9 Zeichen des aktuellen Timestamps in ms.
private fun createReuestIdFromUnixEpoche(): String {
  val time = Instant.now().toEpochMilli().toString(10)
  return time.substring(time.length - 9)
}