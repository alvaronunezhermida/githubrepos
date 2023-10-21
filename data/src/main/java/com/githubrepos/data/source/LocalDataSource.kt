package com.githubrepos.data.source

import arrow.core.Either
import com.githubrepos.domain.Empty
import com.githubrepos.domain.Error
import com.githubrepos.domain.Repo
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    val repos: Flow<List<Repo>>

    suspend fun isReposListEmpty(): Boolean
    suspend fun saveRepos(repos: List<Repo>): Either<Error, Empty>
    suspend fun updateRepo(repo: Repo): Either<Error, Empty>
    suspend fun getRepo(repoId: Int): Either<Error, Repo>
}