package com.github.bschramke.comdirect.rest.sample.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.bschramke.comdirect.rest.ComdirectApiClient

fun main(args: Array<String>) = ComdirectCliApp().main(args)

class ComdirectCliApp : CliktCommand() {
  val clientId: String by option(help="The client_id you got from comdirect.").prompt("Your client_id")
  val clientSecret: String by option(help="The client_secret you got from comdirect.").prompt("Your client_secret")
  val zugangsnummer: String by option(help="The Zugangsnummer you use for login to comdirect web portal.").prompt("Your Zugangsnummer")
  val pin: String by option(help="The PIN you use for login to comdirect web portal.").prompt("Your PIN")

  override fun run() {
    val apiClient = ComdirectApiClient(clientId, clientSecret)
    val result = apiClient.loginCustomer(zugangsnummer, pin)
    echo("Got session list with ${result.size} entries.")
    result.forEach {
      echo("Found session with id ${it.identifier}")
      echo("Session TAN is ${if(it.isSessionTanActive) "activated" else "not activated"}")
      echo("Session ${if(it.hasActivated2FA) "has" else "has not"} activated 2FA")
    }
  }
}