package com.githubrepos.usecases

import arrow.core.Either
import com.githubrepos.data.repository.Repository
import com.githubrepos.domain.Empty
import com.githubrepos.domain.Error
import com.githubrepos.domain.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadAllReposUseCase @Inject constructor(private val repository: Repository) :
    BaseUseCase<Void, List<Repo>>() {

    override fun run(params: Void?): Flow<Either<Error, List<Repo>>> =
        repository.loadAllRepos()
}