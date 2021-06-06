package com.github.bschramke.comdirect.rest.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant
import java.util.*

@Serializable
data class ClientRequestId(
  @SerialName("sessionId")
  val sessionId: String,
  @SerialName("requestId")
  val requestId: String
)

@Serializable
data class XHttpRequestInfo(
  @SerialName("clientRequestId")
  val clientRequestId: ClientRequestId
)

fun createXHttpRequestInfo() = XHttpRequestInfo(createClientRequestId())

private fun createClientRequestId() = ClientRequestId(
  sessionId = UUID.randomUUID().toString(),
  requestId = createReuestIdFromUnixEpoche()
)

// Request ID aus den letzten 9 Zeichen des aktuellen Timestamps in ms.
private fun createReuestIdFromUnixEpoche(): String {
  val time = Instant.now().toEpochMilli().toString(10)
  return time.substring(time.length - 9)
}