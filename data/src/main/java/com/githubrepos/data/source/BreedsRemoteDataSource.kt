package com.githubrepos.data.source

import arrow.core.Either
import com.githubrepos.domain.Breed
import com.githubrepos.domain.BreedImage
import com.githubrepos.domain.Error

interface BreedsRemoteDataSource {
    suspend fun getAllBreeds(): Either<Error, List<Breed>>
    suspend fun getBreedImages(breedName: String, quantity: Int): Either<Error, List<BreedImage>>
}