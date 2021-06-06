package com.github.bschramke.comdirect.rest.internal.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

internal typealias SessionInfoArray = Array<SessionInfo>

@Serializable
internal data class SessionInfo(
  @SerialName("identifier")
  val identifier: String,
  @SerialName("sessionTanActive")
  val isSessionTanActive: Boolean,
  @SerialName("activated2FA")
  val hasActivated2FA: Boolean
)
