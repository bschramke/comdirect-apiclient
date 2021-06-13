package com.github.bschramke.comdirect.rest.model

sealed class TanChallenge {
  /**
   * @param id the id of this TAN-Challenge
   */
  data class MobileTanChallenge(val id:String, val phonenumber:String): TanChallenge()
  /**
   * @param id the id of this TAN-Challenge
   */
  data class PushTanChallenge(val id:String): TanChallenge()
  /**
   * @param id the id of this TAN-Challenge
   */
  data class PhotoTanChallenge(val id:String, val image:ByteArray): TanChallenge() {
    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (javaClass != other?.javaClass) return false

      other as PhotoTanChallenge

      if (id != other.id) return false
      if (!image.contentEquals(other.image)) return false

      return true
    }

    override fun hashCode(): Int {
      var result = id.hashCode()
      result = 31 * result + image.contentHashCode()
      return result
    }
  }
}
