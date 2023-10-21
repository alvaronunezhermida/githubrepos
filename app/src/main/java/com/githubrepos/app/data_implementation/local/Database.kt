package com.githubrepos.app.data_implementation.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.githubrepos.app.data_implementation.local.converters.OwnerConverter
import com.githubrepos.app.data_implementation.local.daos.RepoDao
import com.githubrepos.app.data_implementation.local.entities.OwnerEntity
import com.githubrepos.app.data_implementation.local.entities.RepoEntity

@Database(
    entities = [
        RepoEntity::class,
        OwnerEntity::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(
    OwnerConverter::class
)
abstract class Database : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}