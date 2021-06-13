package com.github.bschramke.comdirect.rest.internal.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class TanType {
  @SerialName("M_TAN")
  M_TAN,
  @SerialName("P_TAN")
  P_TAN,
  @SerialName("P_TAN_PUSH")
  P_TAN_PUSH
}
