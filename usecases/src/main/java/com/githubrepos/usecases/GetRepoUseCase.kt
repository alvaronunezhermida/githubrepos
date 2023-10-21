package com.githubrepos.usecases

import arrow.core.Either
import arrow.core.left
import com.githubrepos.data.repository.Repository
import com.githubrepos.domain.Error
import com.githubrepos.domain.Repo
import com.githubrepos.usecases.GetRepoUseCase.Params
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRepoUseCase @Inject constructor(private val repository: Repository) :
    BaseUseCase<Params, Repo>() {

    override fun run(params: Params?): Flow<Either<Error, Repo>> = params?.run {
        repository.getRepo(repoId = params.repoId)
    } ?: flow {
        emit(Error.NullParams.left())
    }

    data class Params(
        val repoId: Int
    )
}