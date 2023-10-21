package com.githubrepos.app.data_implementation.local.daos

import androidx.room.*
import com.githubrepos.app.data_implementation.local.entities.RepoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RepoDao {

    @Query("SELECT * FROM RepoEntity")
    fun getAll(): Flow<List<RepoEntity>>

    @Query("SELECT * FROM RepoEntity WHERE id = :repoId")
    fun getRepo(repoId: Int): RepoEntity

    @Update
    fun updateRepo(repo: RepoEntity)

    @Query("SELECT COUNT(id) FROM RepoEntity")
    fun reposCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRepos(repos: List<RepoEntity>)

}