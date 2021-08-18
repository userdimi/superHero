package de.colognecode.superheroes.repository.model

import com.google.gson.annotations.SerializedName

data class ResultsItem(

    // A set of public web site URLs for the resource
    @field:SerializedName("urls")
    val urls: List<UrlsItem?>? = null,

    // The representative image for this character.
    @field:SerializedName("thumbnail")
    val thumbnail: Thumbnail? = null,

    // A resource list of stories in which this character appears.
    @field:SerializedName("stories")
    val stories: Stories? = null,

    // A resource list of series in which this character appears.
    @field:SerializedName("series")
    val series: Series? = null,

    // A resource list containing comics which feature this character.
    @field:SerializedName("comics")
    val comics: Comics? = null,

    // The name of the character.
    @field:SerializedName("name")
    val name: String? = null,

    // A short bio or description of the character.
    @field:SerializedName("description")
    val description: String? = null,

    // The date the resource was most recently modified.
    @field:SerializedName("modified")
    val modified: String? = null,

    // The unique ID of the character resource.
    @field:SerializedName("id")
    val id: String? = null,

    // The canonical URL identifier for this resource.
    @field:SerializedName("resourceURI")
    val resourceURI: String? = null,

    // List of events in which this character appears.
    @field:SerializedName("events")
    val events: Events? = null
)
