package com.github.bschramke.comdirect.rest.sample.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.bschramke.comdirect.rest.ComdirectApiClient

fun main(args: Array<String>) = ComdirectCliApp().main(args)

class ComdirectCliApp : CliktCommand() {
  val clientId: String by option(help="The client_id you got from comdirect.").prompt("Your client_id")
  val clientSecret: String by option(help="The client_secret you got from comdirect.").prompt("Your client_secret")

  override fun run() {
    val apiClient = ComdirectApiClient(clientId, clientSecret)
  }
}