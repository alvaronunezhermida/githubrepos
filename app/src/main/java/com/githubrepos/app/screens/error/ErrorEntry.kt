package com.githubrepos.app.screens.error

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


sealed class ErrorEntry : Parcelable {
    @Parcelize
    data class Custom(
        val title: String,
        val detail: String
    ) : ErrorEntry()

    @Parcelize
    object NullParams : ErrorEntry()

    @Parcelize
    object Unknown : ErrorEntry()
}