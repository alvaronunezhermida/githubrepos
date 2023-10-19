package com.githubrepos.usecases

import arrow.core.Either
import arrow.core.left
import com.githubrepos.domain.Error
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

abstract class BaseUseCase<in P, out M>(
    private val dispatcher: CoroutineContext = Dispatchers.Default
) {

    /**
     * Invokes the operator function to execute an asynchronous operation with optional parameters.
     *
     * This operator function allows you to initiate the execution of an asynchronous operation, specified by the [run] function, with optional input parameters [params].
     * It returns a [Flow] of [Either] results that represent either a successful outcome ([Either.Right]) or an error ([Either.Left]).
     *
     * @param params Optional input parameters of type [P] for the asynchronous operation. Default is `null`.
     * @return A [Flow] of [Either] results:
     *   - If the asynchronous operation completes successfully, the result is emitted as [Either.Right].
     *   - If an exception occurs during the operation, an [Error.Unknown] is emitted as [Either.Left].
     *
     * @see run
     * @see Error
     * @see Either
     * @see Flow
     * @see Dispatcher
     */

    operator fun invoke(params: P? = null): Flow<Either<Error, M>> = run(params = params)
        .catch { emit(Error.Unknown.left()) }
        .flowOn(context = dispatcher)

    protected abstract fun run(params: P?): Flow<Either<Error, M>>

}