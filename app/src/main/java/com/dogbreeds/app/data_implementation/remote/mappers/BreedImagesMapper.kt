package com.dogbreeds.app.data_implementation.remote.mappers

import com.dogbreeds.app.data_implementation.remote.models.BreedImagesResponseDTO
import com.dogbreeds.domain.BreedImage

fun BreedImagesResponseDTO.toDomain(): List<BreedImage> =
    breedImages?.map { BreedImage(it) } ?: emptyList()