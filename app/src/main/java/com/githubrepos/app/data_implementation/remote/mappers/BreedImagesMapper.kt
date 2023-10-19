package com.githubrepos.app.data_implementation.remote.mappers

import com.githubrepos.app.data_implementation.remote.models.BreedImagesResponseDTO
import com.githubrepos.domain.BreedImage

fun BreedImagesResponseDTO.toDomain(): List<BreedImage> =
    breedImages?.map { BreedImage(it) } ?: emptyList()