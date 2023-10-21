package com.githubrepos.usecases

import arrow.core.Either
import arrow.core.left
import com.githubrepos.data.repository.Repository
import com.githubrepos.domain.Empty
import com.githubrepos.domain.Error
import com.githubrepos.usecases.CountStargazersUseCase.Params
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CountStargazersUseCase @Inject constructor(private val repository: Repository) :
    BaseUseCase<Params, Empty>() {

    override fun run(params: Params?): Flow<Either<Error, Empty>> = params?.run {
        repository.countStargazers(repoId = params.repoId, stargazersUrl = params.stargazersUrl)
    } ?: flow {
        emit(Error.Unknown.left())
    }


    data class Params(
        val repoId: Int,
        val stargazersUrl: String
    )
}