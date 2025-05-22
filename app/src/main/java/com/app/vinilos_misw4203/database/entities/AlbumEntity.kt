package com.app.vinilos_misw4203.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.vinilos_misw4203.models.Album

@Entity(tableName = "albums")
data class AlbumEntity(
    @PrimaryKey val albumId: Int,
    val name: String,
    val coverUrl: String,
    val releaseDate: String,
    val description: String,
    val genre: String,
    val recordLabel: String,
    val lastFetched: Long = System.currentTimeMillis()
) {
    fun toAlbum(): Album = Album(albumId, name, coverUrl, releaseDate, description, genre, recordLabel)
    companion object {
        fun fromAlbum(a: Album) = AlbumEntity(
            a.albumId, a.name, a.coverUrl, a.releaseDate, a.description, a.genre, a.recordLabel
        )
    }
}