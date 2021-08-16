package de.colognecode.superheroes.data.model

import com.google.gson.annotations.SerializedName

/**
 * 	A resource list of series in which this character appears.
 */
data class Series(

    @field:SerializedName("collectionURI")
    val collectionURI: String? = null,

    @field:SerializedName("available")
    val available: String? = null,

    @field:SerializedName("returned")
    val returned: String? = null,

    @field:SerializedName("items")
    val items: List<ItemsItem?>? = null
)