package de.colognecode.superheroes.repository.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = SuperHero::class,
        parentColumns = ["id"],
        childColumns = ["superHero"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Comic(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val comicName: String?,
    @ColumnInfo(name = "resourceURI") val resourceURI: String?,
    @ColumnInfo(index = true) val superHero: String?
)
