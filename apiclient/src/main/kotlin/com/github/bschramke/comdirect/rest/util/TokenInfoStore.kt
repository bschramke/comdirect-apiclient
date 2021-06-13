package com.github.bschramke.comdirect.rest.util

import com.github.bschramke.comdirect.rest.model.TokenInfo

interface TokenInfoStore {
  fun writeTokenInfo(value:TokenInfo)
  fun readTokenInfo():TokenInfo?
  fun hasTokenInfo():Boolean
  fun removeTokenInfo()
}

