package com.github.bschramke.comdirect.rest.internal.interfaces

import com.github.bschramke.comdirect.rest.internal.models.SessionInfo
import com.github.bschramke.comdirect.rest.internal.models.SessionInfoArray
import retrofit2.Call
import retrofit2.http.*

internal interface SessionApi {

  @Headers(
    "Accept: application/json",
    "Content-Type: application/json"
  )
  @GET("/api/session/clients/user/v1/sessions")
  fun getSessions(
    @Header("Authorization") bearerToken: String,
    @Header("x-http-request-info") requestInfo: String
  ): Call<SessionInfoArray>

  @Headers(
    "Accept: application/json",
    "Content-Type: application/json"
  )
  @POST("/api/session/clients/user/v1/sessions/{identifier}/validate")
  fun requestSessionTan(
    @Header("Authorization") bearerToken: String,
    @Header("x-http-request-info") requestInfo: String,
    @Path("identifier") sessionIdentifier: String,
    @Body session: SessionInfo
  ): Call<SessionInfo>
}