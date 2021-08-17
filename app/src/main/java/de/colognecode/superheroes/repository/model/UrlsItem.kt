package de.colognecode.superheroes.repository.model

import com.google.gson.annotations.SerializedName

/**
 * A set of public web site URLs for the resource.
 */
data class UrlsItem(

    // Text identifier for the URL.
    @field:SerializedName("type")
    val type: String? = null,

    // Full URL (including scheme, domain, and path).
    @field:SerializedName("url")
    val url: String? = null
)
