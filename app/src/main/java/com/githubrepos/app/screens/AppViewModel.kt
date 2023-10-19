package com.githubrepos.app.screens

import com.githubrepos.app.navigation.AppNavigator
import com.githubrepos.domain.Error

abstract class AppViewModel(
    protected val appNavigator: AppNavigator,
) : BaseViewModel() {

    override fun handleError(error: Error) {
        appNavigator.toError(error = error)
    }

}