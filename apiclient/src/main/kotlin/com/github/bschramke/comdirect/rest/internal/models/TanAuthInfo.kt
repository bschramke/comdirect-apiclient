package com.github.bschramke.comdirect.rest.internal.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TanAuthInfo(
  @SerialName("id")
  val id: String,
  @SerialName("typ")
  val typ: String,
  @SerialName("challenge")
  val challenge: String,
  @SerialName("availableTypes")
  val availableTypes: Array<String>
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as TanAuthInfo

    if (id != other.id) return false
    if (typ != other.typ) return false
    if (challenge != other.challenge) return false
    if (!availableTypes.contentEquals(other.availableTypes)) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id.hashCode()
    result = 31 * result + typ.hashCode()
    result = 31 * result + challenge.hashCode()
    result = 31 * result + availableTypes.contentHashCode()
    return result
  }
}
