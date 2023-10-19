package com.dogbreeds.app.data_implementation.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BreedEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val breedName: String
)