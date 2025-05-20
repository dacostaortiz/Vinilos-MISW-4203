package com.app.vinilos_misw4203.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.vinilos_misw4203.database.daos.AlbumDao
import com.app.vinilos_misw4203.database.daos.PerformerDao
import com.app.vinilos_misw4203.database.entities.AlbumEntity
import com.app.vinilos_misw4203.database.entities.PerformerEntity

@Database(entities = [AlbumEntity::class, PerformerEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
    abstract fun performerDao(): PerformerDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "vinilos_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}