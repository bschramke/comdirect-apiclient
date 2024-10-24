package com.github.bschramke.comdirect.rest.sample.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.output.TermUi
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.bschramke.comdirect.rest.ComdirectApiClient
import com.github.bschramke.comdirect.rest.model.LoginResult
import com.github.bschramke.comdirect.rest.model.TanChallenge

fun main(args: Array<String>) = ComdirectCliApp().main(args)

val apiClientKeyValueStore by lazy { InMemoryKeyValueStore() }

class ComdirectCliApp : CliktCommand() {
  val clientId: String by option(help="The client_id you got from comdirect.").prompt("Your client_id")
  val clientSecret: String by option(help="The client_secret you got from comdirect.").prompt("Your client_secret")
  val zugangsnummer: String by option(help="The Zugangsnummer you use for login to comdirect web portal.").prompt("Your Zugangsnummer")
  val pin: String by option(help="The PIN you use for login to comdirect web portal.").prompt("Your PIN")

  override fun run() {
    val apiClient = ComdirectApiClient(clientId, clientSecret, ComdirectApiClient.defaultConfig(apiClientKeyValueStore, apiClientKeyValueStore))
    val result = apiClient.loginCustomer(zugangsnummer, pin, ::onTanChallenge)

    when(result) {
      is LoginResult.Failure -> echo("Login was not successful")
      is LoginResult.Success -> echo("Login was successful")
    }
  }

  private fun onTanChallenge(challenge: TanChallenge):String? = when(challenge) {
    is TanChallenge.MobileTanChallenge -> onMobileTanChallenge(challenge)
    is TanChallenge.PhotoTanChallenge -> onPhotoTanChallenge(challenge)
    is TanChallenge.PushTanChallenge -> onPushTanChallenge(challenge)
  }

  private fun onMobileTanChallenge(challenge: TanChallenge.MobileTanChallenge):String? {
    TermUi.echo("We have send a TAN to your phone ${challenge.phonenumber}.")
    return TermUi.prompt("Enter TAN")
  }

  private fun onPhotoTanChallenge(challenge: TanChallenge.PhotoTanChallenge):String? {
    TermUi.echo("PhotoTAN is currently not supported by this app.")
    return null
  }

  private fun onPushTanChallenge(challenge: TanChallenge.PushTanChallenge):String? {
    TermUi.echo("PushTAN is currently not supported by this app.")
    return null
  }
}