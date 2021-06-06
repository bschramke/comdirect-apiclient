package com.github.bschramke.comdirect.rest.internal.models

import com.github.bschramke.comdirect.rest.model.TokenInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.exp

@Serializable
internal data class TokenResult(
  @SerialName("access_token")
  val accessToken: String,
  @SerialName("refresh_token")
  val refreshToken: String,
  @SerialName("token_type")
  val tokenType: String,
  @SerialName("expires_in")
  val expiresIn: Int,
  @SerialName("scope")
  val scope: String,
  @SerialName("kdnr")
  val kdnr: String,
  @SerialName("bpid")
  val bpid: Long,
  @SerialName("kontaktId")
  val kontaktId: Long
) {
  val tokenInfo by lazy { TokenInfo(
    accessToken = accessToken,
    refreshToken = refreshToken,
    expiresIn = expiresIn,
    scope = scope
  )}
}
