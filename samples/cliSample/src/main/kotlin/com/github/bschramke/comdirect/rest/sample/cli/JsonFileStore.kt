package com.github.bschramke.comdirect.rest.sample.cli

import com.github.bschramke.comdirect.rest.model.TokenInfo
import com.github.bschramke.comdirect.rest.model.decodeTokenInfoFromJson
import com.github.bschramke.comdirect.rest.model.toJsonString
import com.github.bschramke.comdirect.rest.util.InstantAsStringSerializer
import com.github.bschramke.comdirect.rest.util.KeyValueStore
import com.github.bschramke.comdirect.rest.util.TokenInfoStore
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull
import java.io.File

class JsonFileStore(val jsonFile:File) : KeyValueStore, TokenInfoStore {

  init {
      if(!jsonFile.exists()) {
        jsonFile.parentFile.mkdirs()
        jsonFile.createNewFile()
        jsonFile.writeText("{}")
      }

      require(jsonFile.isFile)
      require(jsonFile.canRead())
      require(jsonFile.canWrite())
  }

  private val parsedContent: JsonObject
    get() {
      val content = jsonFile.readText()
      return Json.parseToJsonElement(content).jsonObject
    }

  override fun read(key: String): Any? {
    return parsedContent[key]
  }

  override fun readString(key: String): String? {
    val elem = parsedContent[key] ?: return null

    return elem.jsonPrimitive.content
  }

  override fun readBoolean(key: String): Boolean? {
    val elem = parsedContent[key] ?: return null

    return elem.jsonPrimitive.boolean
  }

  override fun readInt(key: String): Int? {
    val elem = parsedContent[key] ?: return null

    return elem.jsonPrimitive.intOrNull
  }

  override fun readLong(key: String): Long? {
    val elem = parsedContent[key] ?: return null

    return elem.jsonPrimitive.longOrNull
  }

  override fun write(key: String, value: Any) {
    TODO("Not yet implemented")
  }

  private fun write(key: String, value: JsonElement) {
    val map = parsedContent.toMutableMap()
    map[key] = value
    val encoded = Json.encodeToJsonElement(map)

    jsonFile.writeText(Json.encodeToString(encoded))
  }

  override fun writeString(key: String, value: String) {
    write(key, JsonPrimitive(value))
  }

  override fun writeBoolean(key: String, value: Boolean)  {
    write(key, JsonPrimitive(value))
  }

  override fun writeInt(key: String, value: Int)  {
    write(key, JsonPrimitive(value))
  }

  override fun writeLong(key: String, value: Long)  {
    write(key, JsonPrimitive(value))
  }

  //##########################################################
  //# Implement TokenInfoStore
  //##########################################################
  private val KEY_TOKEN_INFO = "TOKEN_INFO"
  override fun writeTokenInfo(value: TokenInfo) {
    write(KEY_TOKEN_INFO, Json.encodeToJsonElement(value))
  }

  override fun readTokenInfo(): TokenInfo? = decodeTokenInfoFromJson(
    readString(KEY_TOKEN_INFO, "null")
  )

  override fun hasTokenInfo(): Boolean = read(KEY_TOKEN_INFO) != null

  override fun removeTokenInfo() {
    val map = parsedContent.toMutableMap()
    map.remove(KEY_TOKEN_INFO)
    val encoded = Json.encodeToJsonElement(map)

    jsonFile.writeText(Json.encodeToString(encoded))
  }

}