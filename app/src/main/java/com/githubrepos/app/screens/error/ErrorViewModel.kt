package com.githubrepos.app.screens.error

import com.githubrepos.app.navigation.AppNavigator
import com.githubrepos.app.screens.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ErrorViewModel @Inject constructor(private val appNavigator: AppNavigator) :
    BaseViewModel() {

    fun onOkButtonClicked() {
        appNavigator.goBack()
    }

}