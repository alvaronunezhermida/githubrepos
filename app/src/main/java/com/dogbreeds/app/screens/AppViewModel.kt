package com.dogbreeds.app.screens

import com.dogbreeds.app.navigation.AppNavigator
import com.dogbreeds.domain.Error

abstract class AppViewModel(
    protected val appNavigator: AppNavigator,
) : BaseViewModel() {

    override fun handleError(error: Error) {
        appNavigator.toError(error = error)
    }

}