package com.githubrepos.usecases

import arrow.core.Either
import arrow.core.left
import com.githubrepos.data.repository.Repository
import com.githubrepos.domain.Error
import com.githubrepos.usecases.GetLanguageUseCase.Params
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLanguageUseCase @Inject constructor(private val repository: Repository) :
    BaseUseCase<Params, String>() {

    override fun run(params: Params?): Flow<Either<Error, String>> = params?.run {
        repository.getLanguage(repoId = params.repoId, languagesUrl = params.languagesUrl)
    } ?: flow {
        emit(Error.Unknown.left())
    }


    data class Params(
        val repoId: Int,
        val languagesUrl: String
    )
}