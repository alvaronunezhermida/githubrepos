package com.githubrepos.data

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.githubrepos.data.repository.BreedsRepository
import com.githubrepos.data.source.BreedsLocalDataSource
import com.githubrepos.data.source.BreedsRemoteDataSource
import com.githubrepos.domain.Breed
import com.githubrepos.domain.BreedImage
import com.githubrepos.domain.Error
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class BreedsRepositoryTest {

    @Mock
    lateinit var localDataSource: BreedsLocalDataSource

    @Mock
    lateinit var remoteDataSource: BreedsRemoteDataSource

    private lateinit var breedsRepository: BreedsRepository

    private val localBreeds =
        flowOf(listOf(sampleBreed.copy("Australian Terrier"), sampleBreed.copy("American Terrier")))

    @Before
    fun setUp() {
        whenever(localDataSource.breeds).thenReturn(localBreeds)
        breedsRepository = BreedsRepository(remoteDataSource, localDataSource)
    }

    @Test
    fun `Breeds are taken from local data source if available`(): Unit = runTest {
        val result = breedsRepository.breeds

        assertEquals(localBreeds, result)
    }

    @Test
    fun `Breeds are saved to local data source when it's empty`(): Unit = runTest {
        val remoteBreeds = listOf(sampleBreed.copy("Basenji"))
        whenever(localDataSource.isBreedsListEmpty()).thenReturn(true)
        whenever(remoteDataSource.getAllBreeds()).thenReturn(remoteBreeds.right())

        breedsRepository.loadAllBreeds().collect {
            it.fold(
                { error -> assertEquals(Error.Unknown, error) },
                { verify(localDataSource).saveBreeds(remoteBreeds) }
            )
        }


    }

    @Test
    fun `Breed Images are retrieved when call getBreedImages`(): Unit = runTest {
        val remoteBreedImages =
            listOf(sampleBreedImage.copy("https://images.dog.ceo/breeds/terrier-russell/iguet1.jpg"))
        whenever(
            remoteDataSource.getBreedImages(
                "terrier",
                1
            )
        ).thenReturn(remoteBreedImages.right())

        breedsRepository.getBreedImages("terrier", 1).collect {
            it.fold(
                { error -> assertEquals(Error.Unknown, error) },
                { breedImages ->
                    assertEquals(remoteBreedImages, breedImages)
                    verify(remoteDataSource).getBreedImages("terrier", 1)
                }
            )
        }

    }
}

private val sampleBreed = Breed(
    "American Terrier"
)

private val sampleBreedImage = BreedImage(
    "https://images.dog.ceo/breeds/mexicanhairless/n02113978_2787.jpg"
)

private fun <T> doRun(block: () -> Flow<Either<Error, T>>): Flow<Either<Error, T>> = block()
    .catch {
        emit(Error.Unknown.left())
    }.flowOn(Dispatchers.IO)