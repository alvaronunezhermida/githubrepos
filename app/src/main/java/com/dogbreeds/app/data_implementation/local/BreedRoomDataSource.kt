package com.dogbreeds.app.data_implementation.local

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.dogbreeds.app.data_implementation.local.daos.BreedDao
import com.dogbreeds.data.source.BreedsLocalDataSource
import com.dogbreeds.domain.Breed
import com.dogbreeds.domain.Empty
import com.dogbreeds.domain.Error
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BreedRoomDataSource @Inject constructor(private val breedDao: BreedDao) :
    BreedsLocalDataSource {

    override val breeds: Flow<List<Breed>> = breedDao.getAll().map { it.toDomainBreedList() }

    override suspend fun isBreedsListEmpty(): Boolean = breedDao.breedsCount() == 0

    override suspend fun saveBreeds(breeds: List<Breed>): Either<Error, Empty> = try {
        breedDao.insertBreeds(breeds.toBreedEntityList())
        Empty().right()
    } catch (e: Exception) {
        Error.Unknown.left()
    }
}