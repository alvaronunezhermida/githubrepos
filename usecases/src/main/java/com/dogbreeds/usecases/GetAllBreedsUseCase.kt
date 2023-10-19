package com.dogbreeds.usecases

import com.dogbreeds.data.repository.BreedsRepository
import javax.inject.Inject

class GetAllBreedsUseCase @Inject constructor(private val breedsRepository: BreedsRepository) {
    operator fun invoke() = breedsRepository.breeds
}