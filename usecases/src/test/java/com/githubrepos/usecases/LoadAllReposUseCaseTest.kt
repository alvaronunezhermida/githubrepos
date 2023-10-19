package com.githubrepos.usecases

import com.githubrepos.data.repository.Repository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class LoadAllReposUseCaseTest {

    @Test
    fun `Invoke calls repos repository`(): Unit = runBlocking {
        val repository = mock<Repository>()
        val loadAllReposUseCase = LoadAllReposUseCase(repository)

        loadAllReposUseCase()

        verify(repository).loadAllRepos()
    }
}