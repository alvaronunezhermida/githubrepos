package com.githubrepos.app.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import com.githubrepos.domain.Error
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * A base ViewModel class that provides common functionality for managing coroutines and error handling.
 *
 * This class serves as a foundation for implementing ViewModel classes within your Android application.
 *
 * @param job The [SupervisorJob] responsible for managing the lifecycle of coroutines in this ViewModel.
 * @param dispatcher The [CoroutineContext] defining the context in which coroutines are executed, defaulting to the main thread.
 * @param exceptionHandler The optional [CoroutineExceptionHandler] to handle exceptions thrown in coroutines.
 */
open class BaseViewModel(
    private val job: Job = SupervisorJob(),
    private val dispatcher: CoroutineContext = Dispatchers.Main,
    private val exceptionHandler: CoroutineExceptionHandler? = null
) : ViewModel() {

    private var scope: CoroutineScope = initScope()
        get() {
            if (!field.isActive) {
                field = initScope()
            }
            return field
        }

    private fun initScope() = CoroutineScope(
        context = job +
                dispatcher + (
                exceptionHandler
                    ?: CoroutineExceptionHandler { _, throwable ->
                        Log.e(
                            this@BaseViewModel.javaClass.simpleName,
                            Log.getStackTraceString(throwable)
                        )
                    }
                )
    )

    open fun onStarted() {}

    open fun onStop() {}

    protected open fun handleError(error: Error) = Unit
    open fun search(name: String) = Unit

    protected fun launch(body: suspend CoroutineScope.() -> Unit) =
        scope.launch(block = body)

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }

}