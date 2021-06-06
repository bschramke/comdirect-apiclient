package com.github.bschramke.comdirect.rest

import com.github.bschramke.comdirect.rest.interfaces.OAuthApi
import com.github.bschramke.comdirect.rest.model.TokenResult
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.lang.RuntimeException

class ComdirectApiClient(
  private val credentials: ClientCredentials,
  private val config: Config = DEFAULT_CONFIG
) {

  private val oAuthApi by lazy { createOAuthApi() }

  // secondary constructor
  constructor(
    clientId: String,
    clientSecret: String,
    config: Config = DEFAULT_CONFIG
  ) : this(ClientCredentials(clientId, clientSecret), config)

  fun loginCustomer(zugangsnummer: String, pin: String): TokenResult {
    val tokenCall = oAuthApi.token(
      clientId = credentials.clientId,
      clientSecret = credentials.clientSecret,
      grantType = "password",
      username = zugangsnummer,
      password = pin
    )

    val response = tokenCall.execute()

    if(!response.isSuccessful) {
      throw RuntimeException("somthing gone wrong")
    }

    return response.body()!!
  }

  private fun createOAuthApi(): OAuthApi = config.retrofitFactory(
    config.oauthUrl,
    config.okHttpClientFactory())
    .create(OAuthApi::class.java)

  data class ClientCredentials(
    val clientId: String,
    val clientSecret: String
  )

  data class Config(
    val oauthUrl: String,
    val okHttpClientFactory: () -> OkHttpClient,
    val retrofitFactory: (baseUrl: String, client: OkHttpClient) -> Retrofit
  )

  companion object {
    @JvmStatic
    val DEFAULT_CONFIG = Config(
      oauthUrl = "https://api.comdirect.de",
      okHttpClientFactory = ::createDefaultOkHttpClient,
      retrofitFactory = ::createDefaultRetrofit
    )
  }
}