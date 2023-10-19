package com.githubrepos.usecases

import arrow.core.left
import com.githubrepos.data.repository.BreedsRepository
import com.githubrepos.domain.Error
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GetBreedImagesUseCaseTest {

    @Test
    fun `Invoke calls breeds repository`(): Unit = runBlocking {
        val breedsRepository = mock<BreedsRepository>()
        val getBreedImagesUseCase = GetBreedImagesUseCase(breedsRepository)

        getBreedImagesUseCase(GetBreedImagesUseCase.Params(sampleBreed.breedName, 10))
        verify(breedsRepository).getBreedImages(sampleBreed.breedName, 10)
    }



    @Test
    fun `If no params returns NullParams Error`(): Unit = runBlocking {
        val breedsRepository = mock<BreedsRepository>()
        val getBreedImagesUseCase = GetBreedImagesUseCase(breedsRepository)

        getBreedImagesUseCase(null).collect{
            it.fold(
                { error -> Assert.assertEquals(Error.NullParams, error) },
                { Assert.fail() }
            )
        }
    }

}