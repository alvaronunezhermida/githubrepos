package com.dogbreeds.app.data_implementation.remote

import com.dogbreeds.app.data_implementation.remote.models.BreedImagesResponseDTO
import com.dogbreeds.app.data_implementation.remote.models.BreedsResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BreedsApi {

    @GET("breeds/list/all")
    suspend fun getAllBreeds(): Response<BreedsResponseDTO>

    @GET("breed/{breed}/images/random/{quantity}")
    suspend fun getBreedImages(
        @Path("breed", encoded = true) breed: String,
        @Path("quantity") quantity: Int
    ): Response<BreedImagesResponseDTO>

}