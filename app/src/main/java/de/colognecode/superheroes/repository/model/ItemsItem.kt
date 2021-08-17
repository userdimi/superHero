package de.colognecode.superheroes.repository.model

import com.google.gson.annotations.SerializedName

data class ItemsItem(

    @field:SerializedName("name")
    val name: String? = null,

    // The canonical URL identifier for this resource.
    @field:SerializedName("resourceURI")
    val resourceURI: String? = null
)

