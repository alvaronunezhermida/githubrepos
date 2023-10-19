package com.dogbreeds.app.data_implementation.remote.clients

import arrow.core.Either
import com.dogbreeds.data.source.BreedsRemoteDataSource
import com.dogbreeds.domain.*
import com.dogbreeds.app.data_implementation.remote.BaseRemote
import com.dogbreeds.app.data_implementation.remote.BreedsApi
import com.dogbreeds.app.data_implementation.remote.mappers.toDomain
import javax.inject.Inject

class BreedsClient @Inject constructor(
    private val breedsApi: BreedsApi,
) : BreedsRemoteDataSource, BaseRemote() {

    override suspend fun getAllBreeds(): Either<Error, List<Breed>> = doRun(
        getResponse = {
            breedsApi.getAllBreeds()
        },
        map = { dto ->
            dto.toDomain()
        }
    )

    override suspend fun getBreedImages(breedName: String, quantity:Int): Either<Error, List<BreedImage>> = doRun(
        getResponse = {
            breedsApi.getBreedImages(parseSubBreedForUrl(breedName), quantity)
        },
        map = { dto ->
            dto.toDomain()
        }
    )

    private fun parseSubBreedForUrl(breedName: String): String {
        val splittedBreedName = breedName.split(" ")
        return if(splittedBreedName.size > 1) {
            splittedBreedName.reversed().joinToString("/")
        }else{
            breedName
        }
        //return breedName.split(" ").takeLast(2).joinToString("/")
    }
}