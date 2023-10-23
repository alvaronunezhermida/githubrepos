package com.githubrepos.usecases

import arrow.core.Either
import arrow.core.left
import com.githubrepos.data.repository.Repository
import com.githubrepos.domain.Error
import com.githubrepos.usecases.CountForksUseCase.Params
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CountForksUseCase @Inject constructor(private val repository: Repository) :
    BaseUseCase<Params, Int>() {

    override fun run(params: Params?): Flow<Either<Error, Int>> = params?.run {
        repository.countForks(repoId = params.repoId, forksUrl = params.forksUrl)
    } ?: flow {
        emit(Error.NullParams.left())
    }


    data class Params(
        val repoId: Int,
        val forksUrl: String
    )
}