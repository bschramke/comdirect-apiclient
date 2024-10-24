package com.github.bschramke.comdirect.rest.model

import com.github.bschramke.comdirect.rest.util.InstantAsStringSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.Instant

sealed class LoginResult {
  data class Success(val tokens: TokenInfo):LoginResult()
  object Failure:LoginResult()
}

@Serializable
data class TokenInfo(
  @SerialName("accessToken")
  val accessToken: String,
  @SerialName("refreshToken")
  val refreshToken: String,
  @SerialName("expiresAt")
  @Serializable(with = InstantAsStringSerializer::class)
  val expiresAt: Instant,
  @SerialName("scope")
  val scope: String
)

fun TokenInfo?.toJsonString() = Json.encodeToString(this)
fun decodeTokenInfoFromJson(jsonStr:String) = Json.decodeFromString<TokenInfo?>(jsonStr)