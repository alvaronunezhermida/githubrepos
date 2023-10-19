package com.dogbreeds.app.data_implementation.local

import com.dogbreeds.app.data_implementation.local.entities.BreedEntity
import com.dogbreeds.domain.Breed

fun List<BreedEntity>.toDomainBreedList() = map { Breed(it.breedName) }

fun List<Breed>.toBreedEntityList() = map { BreedEntity(null, it.breedName) }