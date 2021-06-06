package com.github.bschramke.comdirect.rest.internal.interfaces

import com.github.bschramke.comdirect.rest.internal.models.TokenResult
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

internal interface OAuthApi {

  @FormUrlEncoded
  @Headers("Accept: application/json")
  @POST("/oauth/token")
  fun token(
    @Field("client_id") clientId:String,
    @Field("client_secret") clientSecret:String,
    @Field("grant_type") grantType:String,
    @Field("username") username:String,
    @Field("password") password:String
  ): Call<TokenResult>
}