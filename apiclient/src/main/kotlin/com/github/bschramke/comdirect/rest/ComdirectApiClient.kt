package com.github.bschramke.comdirect.rest

import com.github.bschramke.comdirect.rest.interfaces.OAuthApi
import com.github.bschramke.comdirect.rest.interfaces.SessionApi
import com.github.bschramke.comdirect.rest.model.SessionResultArray
import com.github.bschramke.comdirect.rest.model.TokenResult
import com.github.bschramke.comdirect.rest.model.XHttpRequestInfo
import com.github.bschramke.comdirect.rest.model.createXHttpRequestInfo
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.lang.RuntimeException

class ComdirectApiClient(
  private val credentials: ClientCredentials,
  private val config: Config = DEFAULT_CONFIG
) {

  private val oAuthApi by lazy { createRetrofitApi(OAuthApi::class.java) }
  private val sessionApi by lazy { createRetrofitApi(SessionApi::class.java) }
  private lateinit var requestInfo:XHttpRequestInfo

  // secondary constructor
  constructor(
    clientId: String,
    clientSecret: String,
    config: Config = DEFAULT_CONFIG
  ) : this(ClientCredentials(clientId, clientSecret), config)

  fun loginCustomer(zugangsnummer: String, pin: String): SessionResultArray {
    val tokenCall = oAuthApi.token(
      clientId = credentials.clientId,
      clientSecret = credentials.clientSecret,
      grantType = "password",
      username = zugangsnummer,
      password = pin
    )

    val tokenResponse = tokenCall.execute()

    if(!tokenResponse.isSuccessful) {
      throw RuntimeException("Failed to request initial oAuth tokens")
    }

    val tokenResult = tokenResponse.body()!!
    requestInfo = createXHttpRequestInfo()

    val sessionCall = sessionApi.getSessions("Bearer ${tokenResult.accessToken}", Json.encodeToString(requestInfo))
    val sessionRequest = sessionCall.request()
    val sessionResponse = sessionCall.execute()

    if(!sessionResponse.isSuccessful) {
      throw RuntimeException("Failed to request session list")
    }

    return sessionResponse.body()!!
  }

  private fun <T> createRetrofitApi(apiInterface: Class<T>) = config.retrofitFactory(
    config.baseUrl,
    config.okHttpClientFactory())
    .create(apiInterface)

  data class ClientCredentials(
    val clientId: String,
    val clientSecret: String
  )

  data class Config(
    val baseUrl: String,
    val okHttpClientFactory: () -> OkHttpClient,
    val retrofitFactory: (baseUrl: String, client: OkHttpClient) -> Retrofit
  )

  companion object {
    @JvmStatic
    val DEFAULT_CONFIG = Config(
      baseUrl = "https://api.comdirect.de",
      okHttpClientFactory = ::createDefaultOkHttpClient,
      retrofitFactory = ::createDefaultRetrofit
    )
  }
}