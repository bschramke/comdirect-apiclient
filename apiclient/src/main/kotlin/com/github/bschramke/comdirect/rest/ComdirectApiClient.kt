package com.github.bschramke.comdirect.rest

class ComdirectApiClient(val credentials: ClientCredentials) {

    constructor(clientId: String, clientSecret: String) : this(ClientCredentials(clientId, clientSecret))

    data class ClientCredentials(
        val clientId: String,
        val clientSecret: String
    )
}