package com.dogbreeds.usecases

import arrow.core.Either
import com.dogbreeds.data.repository.BreedsRepository
import com.dogbreeds.domain.Empty
import com.dogbreeds.domain.Error
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadAllBreedsUseCase @Inject constructor(private val breedsRepository: BreedsRepository) :
    BaseUseCase<Void, Empty>() {

    override fun run(params: Void?): Flow<Either<Error, Empty>> =
        breedsRepository.loadAllBreeds()
}