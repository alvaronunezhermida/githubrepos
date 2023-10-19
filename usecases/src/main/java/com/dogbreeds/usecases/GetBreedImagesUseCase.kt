package com.dogbreeds.usecases

import arrow.core.Either
import arrow.core.left
import com.dogbreeds.data.repository.BreedsRepository
import com.dogbreeds.domain.BreedImage
import com.dogbreeds.domain.Error
import com.dogbreeds.usecases.GetBreedImagesUseCase.Params
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBreedImagesUseCase @Inject constructor(private val breedsRepository: BreedsRepository) :
    BaseUseCase<Params, List<BreedImage>>() {

    override fun run(params: Params?): Flow<Either<Error, List<BreedImage>>> = params?.run {
        breedsRepository.getBreedImages(breedName, quantity)
    } ?: flow {
        emit(Error.NullParams.left())
    }

    data class Params(val breedName: String, val quantity: Int)
}