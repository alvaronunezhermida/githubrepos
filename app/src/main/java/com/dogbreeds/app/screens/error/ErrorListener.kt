package com.dogbreeds.app.screens.error

import com.dogbreeds.domain.Error

interface ErrorListener {
    fun onErrorOkClicked(error: Error)
}