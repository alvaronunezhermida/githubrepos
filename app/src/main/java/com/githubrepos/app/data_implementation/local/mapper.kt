package com.githubrepos.app.data_implementation.local

import com.githubrepos.app.data_implementation.local.entities.BreedEntity
import com.githubrepos.domain.Breed

fun List<BreedEntity>.toDomainBreedList() = map { Breed(it.breedName) }

fun List<Breed>.toBreedEntityList() = map { BreedEntity(null, it.breedName) }