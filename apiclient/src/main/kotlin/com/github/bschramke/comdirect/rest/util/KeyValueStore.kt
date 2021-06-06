package com.github.bschramke.comdirect.rest.util

interface KeyValueStore {

  fun read(key: String): Any?
  fun readString(key: String): String?
  fun readBoolean(key: String): Boolean?
  fun readInt(key: String): Int?
  fun readLong(key: String): Long?

  fun read(key: String, default: Any): Any = read(key) ?: default
  fun readString(key: String, default: String): String = readString(key) ?: default
  fun readBoolean(key: String, default: Boolean): Boolean = readBoolean(key) ?: default
  fun readInt(key: String, default: Int): Int = readInt(key) ?: default
  fun readLong(key: String, default: Long): Long = readLong(key) ?: default

  fun write(key: String, value: Any)
  fun writeString(key: String, value: String)
  fun writeBoolean(key: String, value: Boolean)
  fun writeInt(key: String, value: Int)
  fun writeLong(key: String, value: Long)

}