package com.app.vinilos_misw4203.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.vinilos_misw4203.models.Performer

@Entity(tableName = "performers")
data class PerformerEntity(
    @PrimaryKey val performerId: Int,
    val name: String,
    val image: String,
    val description: String,
    val performerType: String,
    val birthDate: String?,
    val lastFetched: Long = System.currentTimeMillis()
) {
    fun toPerformer(): Performer {
        return Performer(performerId, name, image, description, performerType, birthDate)
    }

    companion object {
        fun fromPerformer(performer: Performer): PerformerEntity {
            return PerformerEntity(
                performerId = performer.performerId,
                name = performer.name,
                image = performer.image,
                description = performer.description,
                performerType = performer.performerType,
                birthDate = performer.birthDate
            )
        }
    }
}