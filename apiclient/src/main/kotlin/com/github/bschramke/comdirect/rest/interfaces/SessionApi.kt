package com.github.bschramke.comdirect.rest.interfaces

import com.github.bschramke.comdirect.rest.model.SessionResultArray
import com.github.bschramke.comdirect.rest.model.XHttpRequestInfo
import retrofit2.Call
import retrofit2.http.*

internal interface SessionApi {

  @Headers(
    "Accept: application/json",
    "Content-Type: application/json"
  )
  @GET("/api/session/clients/user/v1/sessions")
  fun getSessions(
    @Header("Authorization") bearerToken:String,
    @Header("x-http-request-info") requestInfo: String
  ): Call<SessionResultArray>
}