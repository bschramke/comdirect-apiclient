package com.github.bschramke.comdirect.rest.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias SessionResultArray = Array<SessionResult>

@Serializable
data class SessionResult(
  @SerialName("identifier")
  val identifier: String,
  @SerialName("sessionTanActive")
  val isSessionTanActive: Boolean,
  @SerialName("activated2FA")
  val hasActivated2FA: Boolean
)
