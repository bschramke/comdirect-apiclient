package com.github.bschramke.comdirect.rest.sample.cli

import com.github.bschramke.comdirect.rest.model.TokenInfo
import com.github.bschramke.comdirect.rest.model.decodeTokenInfoFromJson
import com.github.bschramke.comdirect.rest.model.toJsonString
import com.github.bschramke.comdirect.rest.util.KeyValueStore
import com.github.bschramke.comdirect.rest.util.TokenInfoStore

class InMemoryKeyValueStore : KeyValueStore, TokenInfoStore {
  private val data = mutableMapOf<String, Any>()

  override fun read(key: String): Any?  = data[key]

  override fun readString(key: String): String? = when (val value = read(key)) {
    is String -> value
    value != null -> value.toString()
    else -> null
  }

  override fun readBoolean(key: String): Boolean? = when (val value = read(key)) {
    is Boolean -> value
    else -> null
  }

  override fun readInt(key: String): Int? = when (val value = read(key)) {
    is Int -> value
    is Number -> value.toInt()
    is String -> value.toIntOrNull(10)
    else -> null
  }

  override fun readLong(key: String): Long? = when (val value = read(key)) {
    is Long -> value
    is Number -> value.toLong()
    is String -> value.toLongOrNull(10)
    else -> null
  }

  override fun write(key: String, value: Any) {
    data[key] = value
  }

  override fun writeString(key: String, value: String) {
    data[key] = value
  }

  override fun writeBoolean(key: String, value: Boolean) {
    data[key] = value
  }

  override fun writeInt(key: String, value: Int) {
    data[key] = value
  }

  override fun writeLong(key: String, value: Long) {
    data[key] = value
  }

  //##########################################################
  //# Implement TokenInfoStore
  //##########################################################
  private val KEY_TOKEN_INFO = "TOKEN_INFO"
  override fun writeTokenInfo(value: TokenInfo) {
    writeString(KEY_TOKEN_INFO, value.toJsonString())
  }

  override fun readTokenInfo(): TokenInfo? = decodeTokenInfoFromJson(
    readString(KEY_TOKEN_INFO, "null")
  )

  override fun hasTokenInfo(): Boolean = read(KEY_TOKEN_INFO) != null

  override fun removeTokenInfo() {
    data.remove(KEY_TOKEN_INFO)
  }
}