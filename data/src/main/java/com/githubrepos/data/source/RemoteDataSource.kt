package com.githubrepos.data.source

import arrow.core.Either
import com.githubrepos.domain.Error
import com.githubrepos.domain.Repo

interface RemoteDataSource {
    suspend fun getAllRepos(): Either<Error, List<Repo>>
    suspend fun countStargazers(stargazersUrl: String): Either<Error, Int>
}