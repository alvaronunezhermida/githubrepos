package com.githubrepos.app.data_implementation.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.githubrepos.app.data_implementation.local.daos.RepoDao
import com.githubrepos.app.data_implementation.local.entities.RepoEntity

@Database(entities = [RepoEntity::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}