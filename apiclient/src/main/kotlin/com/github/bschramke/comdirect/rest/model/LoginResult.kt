package com.github.bschramke.comdirect.rest.model

sealed class LoginResult {
  data class Success(val tokens: TokenInfo):LoginResult()
  object Failure:LoginResult()
}

data class TokenInfo(
  val accessToken: String,
  val refreshToken: String,
  val expiresIn: Int,
  val scope: String
)