package com.githubrepos.app.data_implementation.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.githubrepos.app.data_implementation.local.daos.BreedDao
import com.githubrepos.app.data_implementation.local.entities.BreedEntity

@Database(entities = [BreedEntity::class], version = 1, exportSchema = false)
abstract class BreedDatabase : RoomDatabase() {
    abstract fun breedDao(): BreedDao
}