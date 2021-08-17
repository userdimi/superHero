package de.colognecode.superheroes.repository.network

import java.math.BigInteger
import java.security.MessageDigest


data class ApiKeyQuery(
    val timestamp: String,
    private val privateApiKey: String,
    val publicApiKey: String
) {

    val apiKeyHash = createApiKeyHash()

    private fun createApiKeyHash(): String {
        val md = MessageDigest.getInstance("MD5")
        val apiKeyToHash = "$timestamp$privateApiKey$publicApiKey"
        return BigInteger(1, md.digest(apiKeyToHash.toByteArray())).toString(16).padStart(32, '0')
    }
}
