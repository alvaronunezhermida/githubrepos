package com.githubrepos.usecases

import com.githubrepos.data.repository.Repository
import javax.inject.Inject

class GetAllReposUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke() = repository.repos
}