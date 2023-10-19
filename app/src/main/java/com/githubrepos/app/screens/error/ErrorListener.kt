package com.githubrepos.app.screens.error

import com.githubrepos.domain.Error

interface ErrorListener {
    fun onErrorOkClicked(error: Error)
}