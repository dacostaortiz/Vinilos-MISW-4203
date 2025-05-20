package com.app.vinilos_misw4203.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.vinilos_misw4203.database.entities.PerformerEntity

@Dao
interface PerformerDao {
    @Query("SELECT * FROM performers")
    suspend fun getAllPerformers(): List<PerformerEntity>

    @Query("SELECT * FROM performers WHERE lastFetched >= :timestamp")
    suspend fun getFreshPerformers(timestamp: Long): List<PerformerEntity>

    @Query("SELECT * FROM performers WHERE performerId = :id")
    suspend fun getPerformerById(id: Int): PerformerEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(performers: List<PerformerEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerformer(performer: PerformerEntity)
}