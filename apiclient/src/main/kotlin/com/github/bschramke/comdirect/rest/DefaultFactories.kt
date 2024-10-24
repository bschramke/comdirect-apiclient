package com.github.bschramke.comdirect.rest

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@ExperimentalSerializationApi
fun createDefaultRetrofit(baseUrl:String, okHttpClient: OkHttpClient = createDefaultOkHttpClient()):Retrofit = Retrofit.Builder()
  .baseUrl(baseUrl)
  .client(okHttpClient)
  .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
  .build()

fun createDefaultOkHttpClient():OkHttpClient = OkHttpClient.Builder().build()