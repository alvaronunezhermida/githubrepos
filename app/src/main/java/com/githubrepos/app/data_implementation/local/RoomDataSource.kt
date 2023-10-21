package com.githubrepos.app.data_implementation.local

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.githubrepos.app.data_implementation.local.daos.RepoDao
import com.githubrepos.data.source.LocalDataSource
import com.githubrepos.domain.Empty
import com.githubrepos.domain.Error
import com.githubrepos.domain.Repo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomDataSource @Inject constructor(private val repoDao: RepoDao) :
    LocalDataSource {

    override val repos: Flow<List<Repo>> = repoDao.getAll()
        .map { list -> list.sortedByDescending { it.stargazersCount }.map { repo -> repo.toDomain() } }

    override suspend fun isReposListEmpty(): Boolean = repoDao.reposCount() == 0

    override suspend fun saveRepos(repos: List<Repo>): Either<Error, Empty> = try {
        repoDao.insertRepos(repos.map { it.toEntity() })
        Empty().right()
    } catch (e: Exception) {
        Error.Unknown.left()
    }

    override suspend fun updateRepo(repo: Repo): Either<Error, Empty> = try {
        repoDao.updateRepo(repo.toEntity())
        Empty().right()
    } catch (e: Exception) {
        Error.Unknown.left()
    }

    override suspend fun getRepo(repoId: Int): Either<Error, Repo> = try {
        repoDao.getRepo(repoId).toDomain().right()
    } catch (e: Exception) {
        Error.Unknown.left()
    }
}