package com.github.bschramke.comdirect.rest

import com.github.bschramke.comdirect.rest.internal.interfaces.OAuthApi
import com.github.bschramke.comdirect.rest.internal.interfaces.SessionApi
import com.github.bschramke.comdirect.rest.internal.models.TanAuthInfo
import com.github.bschramke.comdirect.rest.internal.models.XHttpRequestInfo
import com.github.bschramke.comdirect.rest.internal.models.createXHttpRequestInfo
import com.github.bschramke.comdirect.rest.model.*
import com.github.bschramke.comdirect.rest.util.KeyValueStore
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.lang.RuntimeException
import java.util.*

class ComdirectApiClient(
  private val credentials: ClientCredentials,
  private val config: Config
) {
  private val keyValueStore
    get() = config.keyValueStore

  private val oAuthApi by lazy { createRetrofitApi(OAuthApi::class.java) }
  private val sessionApi by lazy { createRetrofitApi(SessionApi::class.java) }
  private lateinit var requestInfo: XHttpRequestInfo

  // secondary constructor
  constructor(
    clientId: String,
    clientSecret: String,
    config: Config
  ) : this(ClientCredentials(clientId, clientSecret), config)

  fun createSessionId(): String = UUID.randomUUID().toString().also {
    keyValueStore.writeString(KEY_SESSION_ID, it)
  }

  fun getOrCreateSessionId(): String = keyValueStore.readString(KEY_SESSION_ID) ?: createSessionId()

  fun loginCustomer(zugangsnummer: String, pin: String): LoginResult {
    // Step1 : Request OAuth tokens
    val tokenCall = oAuthApi.token(
      clientId = credentials.clientId,
      clientSecret = credentials.clientSecret,
      grantType = "password",
      username = zugangsnummer,
      password = pin
    )

    val tokenResponse = tokenCall.execute()

    if (!tokenResponse.isSuccessful) {
      return LoginResult.Failure
    }

    val tokenResult = tokenResponse.body()!!
    requestInfo = createXHttpRequestInfo(createSessionId())

    // Step2 : Request session with access token
    val sessionArrayResponse = sessionApi.getSessions(
      "Bearer ${tokenResult.accessToken}",
      Json.encodeToString(requestInfo)
    )
      .execute()

    if (!sessionArrayResponse.isSuccessful) {
      return LoginResult.Failure
    }

    val sessionInfo = sessionArrayResponse.body()!![0]

    // Step3 : Request session TAN
    val sessionTanRequest = sessionApi.requestSessionTan(
      "Bearer ${tokenResult.accessToken}",
      Json.encodeToString(requestInfo),
      sessionInfo.identifier,
      sessionInfo.copy(
        isSessionTanActive = true,
        hasActivated2FA = true
      )
    )

    val sessionTanResponse = sessionTanRequest.execute()

    if (!sessionTanResponse.isSuccessful) {
      return LoginResult.Failure
    }

    val tanAuthInfo =
      sessionTanResponse.headers()["x-once-authentication-info"]?.let { Json.decodeFromString<TanAuthInfo>(it) }
        ?: throw RuntimeException("Failed to extract tan challenge")

    return LoginResult.Success(tokens = tokenResult.tokenInfo)
  }

  private fun <T> createRetrofitApi(apiInterface: Class<T>) = config.retrofitFactory(
    config.baseUrl,
    config.okHttpClientFactory()
  )
    .create(apiInterface)

  data class ClientCredentials(
    val clientId: String,
    val clientSecret: String
  )

  data class Config(
    val baseUrl: String,
    val keyValueStore: KeyValueStore,
    val okHttpClientFactory: () -> OkHttpClient,
    val retrofitFactory: (baseUrl: String, client: OkHttpClient) -> Retrofit
  )

  companion object {
    fun defaultConfig(keyValueStore: KeyValueStore) = Config(
      baseUrl = "https://api.comdirect.de",
      keyValueStore = keyValueStore,
      okHttpClientFactory = ::createDefaultOkHttpClient,
      retrofitFactory = ::createDefaultRetrofit
    )

    private const val KEY_SESSION_ID = "session_id"
  }
}