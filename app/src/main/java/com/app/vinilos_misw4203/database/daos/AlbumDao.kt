package com.app.vinilos_misw4203.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.vinilos_misw4203.database.entities.AlbumEntity

@Dao
interface AlbumDao {
    @Query("SELECT * FROM albums")
    suspend fun getAllAlbums(): List<AlbumEntity>

    @Query("SELECT * FROM albums WHERE lastFetched >= :timestamp")
    suspend fun getFreshAlbums(timestamp: Long): List<AlbumEntity>

    @Query("SELECT * FROM albums WHERE albumId = :id")
    suspend fun getAlbumById(id: Int): AlbumEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(albums: List<AlbumEntity>)
}