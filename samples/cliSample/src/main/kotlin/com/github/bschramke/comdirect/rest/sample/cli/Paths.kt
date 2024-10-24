package com.github.bschramke.comdirect.rest.sample.cli

import java.nio.file.Path
import java.nio.file.Paths

object Paths {

  fun userHome():Path {
    val envHome = System.getenv("HOME")
    return Paths.get(envHome)
  }

  fun dataHome():Path = userHome().resolve(".local/share/")
  fun configHome():Path = userHome().resolve(".config/")
  fun cacheHome():Path = userHome().resolve(".cache/")

  fun applicationDataDir():Path = dataHome().resolve("comdirect-cli/")
  fun applicationConfigDir():Path = dataHome().resolve("comdirect-cli/")
  fun applicationCacheDir():Path = dataHome().resolve("comdirect-cli/")
}