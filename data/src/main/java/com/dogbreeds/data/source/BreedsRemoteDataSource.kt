package com.dogbreeds.data.source

import arrow.core.Either
import com.dogbreeds.domain.Breed
import com.dogbreeds.domain.BreedImage
import com.dogbreeds.domain.Error

interface BreedsRemoteDataSource {
    suspend fun getAllBreeds(): Either<Error, List<Breed>>
    suspend fun getBreedImages(breedName: String, quantity: Int): Either<Error, List<BreedImage>>
}