package com.githubrepos.data.repository

import arrow.core.Either
import arrow.core.left
import com.githubrepos.domain.Error
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

abstract class BaseRepository {

    /**
     * Executes the specified asynchronous operation represented by the [block] on the IO dispatcher and returns a [Flow] of [Either] results.
     *
     * This function is designed to handle asynchronous operations gracefully, allowing you to catch any exceptions and emit an [Error.Unknown] result if an error occurs.
     *
     * @param block A lambda function that produces a [Flow] of [Either] results, representing an asynchronous operation.
     * @return A [Flow] of [Either] results produced by the [block]:
     *   - If the [block] completes successfully, the result is emitted as [Either.Right].
     *   - If an exception occurs during the execution of [block], an [Error.Unknown] is emitted as [Either.Left].
     *
     * @param T The type of data being operated on within the [block].
     * @see Error
     * @see Either
     * @see Flow
     * @see Dispatchers.IO
     */

    protected fun <T> doRun(block: () -> Flow<Either<Error, T>>): Flow<Either<Error, T>> = block()
        .catch {
            emit(Error.Unknown.left())
        }.flowOn(Dispatchers.IO)

}