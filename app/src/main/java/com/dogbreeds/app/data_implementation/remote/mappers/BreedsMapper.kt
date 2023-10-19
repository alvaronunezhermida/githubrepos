package com.dogbreeds.app.data_implementation.remote.mappers

import com.dogbreeds.app.data_implementation.remote.models.BreedsResponseDTO
import com.dogbreeds.domain.Breed

fun BreedsResponseDTO.toDomain(): List<Breed> {
    val newList = mutableListOf<Breed>()
    breeds?.map { breed ->
        if (breed.value.isEmpty()) {
            newList.add(Breed(breed.key))
        } else {
            breed.value.map {
                newList.add(Breed("$it ${breed.key}"))
            }
        }
    }
    return newList.sortedBy { it.breedName }
}