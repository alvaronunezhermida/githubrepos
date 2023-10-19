package com.githubrepos.usecases

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetAllBreedsUseCaseTest {

    @Test
    fun `Invoke calls breeds repository`(): Unit = runBlocking {
        val sampleBreeds = flowOf(listOf(sampleBreed.copy("hound basset")))
        val getAllBreedsUseCase = GetAllBreedsUseCase(mock {
            on { breeds } doReturn sampleBreeds
        })

        val result = getAllBreedsUseCase()

        Assert.assertEquals(sampleBreeds, result)
    }

}