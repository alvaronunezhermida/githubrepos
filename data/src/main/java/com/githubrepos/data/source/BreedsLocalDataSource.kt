package com.githubrepos.data.source

import arrow.core.Either
import com.githubrepos.domain.Breed
import com.githubrepos.domain.Empty
import com.githubrepos.domain.Error
import kotlinx.coroutines.flow.Flow

interface BreedsLocalDataSource {
    val breeds: Flow<List<Breed>>

    suspend fun isBreedsListEmpty(): Boolean
    suspend fun saveBreeds(breeds: List<Breed>): Either<Error, Empty>
}