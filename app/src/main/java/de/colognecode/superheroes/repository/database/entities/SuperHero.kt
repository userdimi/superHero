package de.colognecode.superheroes.repository.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SuperHero(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "thumbnail") val thumbnail: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "copyright") val copyright: String
)
