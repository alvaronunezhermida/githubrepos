package com.dogbreeds.app.screens.error

import com.dogbreeds.app.navigation.AppNavigator
import com.dogbreeds.app.screens.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ErrorViewModel @Inject constructor(private val appNavigator: AppNavigator) :
    BaseViewModel() {

    fun onOkButtonClicked() {
        appNavigator.goBack()
    }

}