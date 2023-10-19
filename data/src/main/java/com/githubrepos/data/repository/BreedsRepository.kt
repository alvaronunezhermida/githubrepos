package com.githubrepos.data.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.githubrepos.data.source.BreedsLocalDataSource
import com.githubrepos.data.source.BreedsRemoteDataSource
import com.githubrepos.domain.Breed
import com.githubrepos.domain.BreedImage
import com.githubrepos.domain.Empty
import com.githubrepos.domain.Error
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Repository class responsible for managing operations related to breeds.
 *
 * This class serves as an intermediary between data sources (remote and local) and provides methods to access and manipulate breed data.
 *
 * @property breedsRemoteDataSource The remote data source for breed information.
 * @property breedsLocalDataSource The local data source for breed information.
 */
class BreedsRepository @Inject constructor(
    private val breedsRemoteDataSource: BreedsRemoteDataSource,
    private val breedsLocalDataSource: BreedsLocalDataSource
) : BaseRepository() {

    /**
     * A [Flow] representing the list of breeds retrieved from the local data source.
     *
     * This property allows you to observe changes to the list of breeds in a reactive manner.
     *
     * @see BreedsLocalDataSource.breeds
     */
    val breeds: Flow<List<Breed>> get() = breedsLocalDataSource.breeds

    /**
     * Loads all breeds from the remote data source and saves them to the local data source.
     *
     * If the local data source is empty, this method fetches all breeds from the remote source and stores them locally.
     * If an error occurs during the process, it emits an [Error.Unknown] result.
     *
     * @return A [Flow] of [Either] indicating the operation result:
     *   - If the operation is successful, [Empty] is emitted as [Either.Right].
     *   - If an error occurs, [Error.Unknown] is emitted as [Either.Left].
     */
    fun loadAllBreeds(): Flow<Either<Error, Empty>> = doRun {
        flow {
            if (breedsLocalDataSource.isBreedsListEmpty()) {
                val breeds = breedsRemoteDataSource.getAllBreeds()
                breeds.fold(
                    ifLeft = { emit(Error.Unknown.left()) },
                    ifRight = {
                        breedsLocalDataSource.saveBreeds(it)
                        emit(Empty().right())
                    }
                )
            } else {
                emit(Empty().right())
            }
        }
    }

    /**
     * Retrieves images of a specific breed from the remote data source.
     *
     * @param breedName The name of the breed to fetch images for.
     * @param quantity The number of images to retrieve.
     * @return A [Flow] of [Either] containing the list of [BreedImage] objects:
     *   - If the operation is successful, the list of images is emitted as [Either.Right].
     *   - If an error occurs, [Error.Unknown] is emitted as [Either.Left].
     */
    fun getBreedImages(breedName: String, quantity: Int): Flow<Either<Error, List<BreedImage>>> =
        doRun {
            flow {
                emit(breedsRemoteDataSource.getBreedImages(breedName, quantity))
            }
        }

}