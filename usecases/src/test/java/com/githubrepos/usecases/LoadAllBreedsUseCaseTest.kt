package com.githubrepos.usecases

import com.githubrepos.data.repository.BreedsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class LoadAllBreedsUseCaseTest {

    @Test
    fun `Invoke calls movies repository`(): Unit = runBlocking {
        val breedsRepository = mock<BreedsRepository>()
        val loadAllBreedsUseCase = LoadAllBreedsUseCase(breedsRepository)

        loadAllBreedsUseCase()

        verify(breedsRepository).loadAllBreeds()
    }
}