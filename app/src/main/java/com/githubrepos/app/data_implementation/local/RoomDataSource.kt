package com.githubrepos.app.data_implementation.local

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.githubrepos.app.data_implementation.local.daos.RepoDao
import com.githubrepos.data.source.LocalDataSource
import com.githubrepos.domain.Repo
import com.githubrepos.domain.Empty
import com.githubrepos.domain.Error
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomDataSource @Inject constructor(private val repoDao: RepoDao) :
    LocalDataSource {

    override val repos: Flow<List<Repo>> = repoDao.getAll().map { it.map { repo -> repo.toDomain() } }

    override suspend fun isReposListEmpty(): Boolean = repoDao.reposCount() == 0

    override suspend fun saveRepos(repos: List<Repo>): Either<Error, Empty> = try {
        repoDao.insertRepos(repos.map { it.toEntity() })
        Empty().right()
    } catch (e: Exception) {
        Error.Unknown.left()
    }

    override suspend fun saveStargazersCount(repoId: Int, count: Int): Either<Error, Empty> = try {
        val repo = repoDao.getRepo(repoId)
        repo.stargazersCount = count
        repoDao.updateRepo(repo)
        Empty().right()
    } catch (e: Exception) {
        Error.Unknown.left()
    }
}