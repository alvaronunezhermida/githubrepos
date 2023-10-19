package com.githubrepos.usecases

import arrow.core.Either
import com.githubrepos.data.repository.BreedsRepository
import com.githubrepos.domain.Empty
import com.githubrepos.domain.Error
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadAllBreedsUseCase @Inject constructor(private val breedsRepository: BreedsRepository) :
    BaseUseCase<Void, Empty>() {

    override fun run(params: Void?): Flow<Either<Error, Empty>> =
        breedsRepository.loadAllBreeds()
}