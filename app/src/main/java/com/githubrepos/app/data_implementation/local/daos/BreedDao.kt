package com.githubrepos.app.data_implementation.local.daos

import androidx.room.*
import com.githubrepos.app.data_implementation.local.entities.BreedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedDao {

    @Query("SELECT * FROM BreedEntity")
    fun getAll(): Flow<List<BreedEntity>>

    @Query("SELECT COUNT(id) FROM BreedEntity")
    fun breedsCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBreeds(breeds: List<BreedEntity>)

}