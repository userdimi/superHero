package de.colognecode.superheroes.repository.model

import com.google.gson.annotations.SerializedName

/**
 * The representative image for this character.
 */
data class Thumbnail(

    // Directory path of to the image.
    @field:SerializedName("path")
    val path: String? = null,

    // The file extension for the image.
    @field:SerializedName("extension")
    val extension: String? = null
)
