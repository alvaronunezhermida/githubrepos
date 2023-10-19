package com.githubrepos.usecases

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetAllReposUseCaseTest {

    @Test
    fun `Invoke calls repos repository`(): Unit = runBlocking {
        val sampleRepos = flowOf(listOf(sampleRepo))
        val getAllReposUseCase = GetAllReposUseCase(mock {
            on { repos } doReturn sampleRepos
        })

        val result = getAllReposUseCase()

        Assert.assertEquals(sampleRepos, result)
    }

}