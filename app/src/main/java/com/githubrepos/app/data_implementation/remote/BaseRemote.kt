package com.githubrepos.app.data_implementation.remote

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.githubrepos.domain.Error
import retrofit2.Response
import timber.log.Timber

abstract class BaseRemote {

    protected suspend fun <Dto, Domain> doRun(
        getResponse: suspend () -> Response<Dto>,
        map: suspend (Dto) -> Domain
    ): Either<Error, Domain> = try {
        invokeService(getResponse = getResponse, retry = true, map = map)
    } catch (exception: Exception) {
        Timber.e(exception)
        Either.Left(Error.Unknown)
    }

    private suspend fun <Dto, Domain> invokeService(
        getResponse: suspend () -> Response<Dto>,
        retry: Boolean,
        map: suspend (Dto) -> Domain
    ): Either<Error, Domain> {
        val response = getResponse()
        Timber.d(response.message())
        return if (response.isSuccessful) {
            response.body()?.let { dto ->
                map(dto).right()
            } ?: Error.NullBody.left()
        } else {
            Error.Unknown.left()
        }
    }

}