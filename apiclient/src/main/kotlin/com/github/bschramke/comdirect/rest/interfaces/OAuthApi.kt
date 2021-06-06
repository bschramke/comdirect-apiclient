package com.github.bschramke.comdirect.rest.interfaces

import com.github.bschramke.comdirect.rest.model.TokenResult
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

internal interface OAuthApi {

  @FormUrlEncoded
  @POST("/oauth/token")
  fun token(
    @Field("client_id") clientId:String,
    @Field("client_secret") clientSecret:String,
    @Field("grant_type") grantType:String,
    @Field("username") username:String,
    @Field("password") password:String
  ): Call<TokenResult>
}