package com.github.bschramke.comdirect.rest.internal.models

import com.github.bschramke.comdirect.rest.model.TanChallenge
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okio.ByteString.Companion.decodeBase64
import java.util.*

@Serializable
internal data class TanAuthInfo(
  @SerialName("id")
  val id: String,
  @SerialName("typ")
  val type: TanType,
  @SerialName("challenge")
  val challenge: String,
  @SerialName("availableTypes")
  val availableTypes: Array<TanType>
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as TanAuthInfo

    if (id != other.id) return false
    if (type != other.type) return false
    if (challenge != other.challenge) return false
    if (!availableTypes.contentEquals(other.availableTypes)) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id.hashCode()
    result = 31 * result + type.hashCode()
    result = 31 * result + challenge.hashCode()
    result = 31 * result + availableTypes.contentHashCode()
    return result
  }

  fun toTanChallenge(): TanChallenge = when(type) {
    TanType.M_TAN -> TanChallenge.MobileTanChallenge(id, challenge)
    TanType.P_TAN -> {
      val decodedImageData = Base64.getDecoder().decode(challenge)
      TanChallenge.PhotoTanChallenge(id, decodedImageData)
    }
    TanType.P_TAN_PUSH -> TanChallenge.PushTanChallenge(id)
  }
}
