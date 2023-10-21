package com.githubrepos.usecases

import arrow.core.Either
import arrow.core.left
import com.githubrepos.data.repository.Repository
import com.githubrepos.domain.Error
import com.githubrepos.usecases.CountStargazersUseCase.Params
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CountStargazersUseCase @Inject constructor(private val repository: Repository) :
    BaseUseCase<Params, Int>() {

    override fun run(params: Params?): Flow<Either<Error, Int>> = params?.run {
        repository.countStargazers(stargazersUrl = params.stargazersUrl)
    } ?: flow {
        emit(Error.Unknown.left())
    }


    data class Params(
        val stargazersUrl: String,
    )
}