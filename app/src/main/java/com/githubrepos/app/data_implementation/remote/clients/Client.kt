package com.githubrepos.app.data_implementation.remote.clients

import arrow.core.Either
import com.githubrepos.app.data_implementation.remote.BaseRemote
import com.githubrepos.app.data_implementation.remote.GithubApi
import com.githubrepos.app.data_implementation.remote.mappers.toDomain
import com.githubrepos.data.source.RemoteDataSource
import com.githubrepos.domain.Error
import com.githubrepos.domain.Repo
import javax.inject.Inject

class Client @Inject constructor(
    private val githubApi: GithubApi,
) : RemoteDataSource, BaseRemote() {

    override suspend fun getAllRepos(): Either<Error, List<Repo>> = doRun(
        getResponse = {
            githubApi.getAllRepos()
        },
        map = { dto ->
            dto.map { it.toDomain() }
        }
    )

    override suspend fun countStargazers(stargazersUrl: String): Either<Error, Int> = doRun(
        getResponse = {
            githubApi.getStargazers(stargazersUrl)
        },
        map = { dto ->
            dto.size
        }
    )

    override suspend fun countForks(forksUrl: String): Either<Error, Int> = doRun(
        getResponse = {
            githubApi.getStargazers(forksUrl)
        },
        map = { dto ->
            dto.size
        }
    )

    override suspend fun getLanguage(languagesUrl: String): Either<Error, String> = doRun(
        getResponse = {
            githubApi.getLanguages(languagesUrl)
        },
        map = { dto ->
            dto.keys.first()
        }
    )
}