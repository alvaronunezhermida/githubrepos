package com.dogbreeds.data.source

import arrow.core.Either
import com.dogbreeds.domain.Breed
import com.dogbreeds.domain.Empty
import com.dogbreeds.domain.Error
import kotlinx.coroutines.flow.Flow

interface BreedsLocalDataSource {
    val breeds: Flow<List<Breed>>

    suspend fun isBreedsListEmpty(): Boolean
    suspend fun saveBreeds(breeds: List<Breed>): Either<Error, Empty>
}