package com.dogbreeds.app.mappers

import com.dogbreeds.app.screens.error.ErrorEntry
import com.dogbreeds.domain.Error

fun Error.toEntry(): ErrorEntry = when (this) {
    is Error.Custom -> ErrorEntry.Custom(
        title = title,
        detail = detail
    )

    Error.NullParams -> ErrorEntry.NullParams
    else -> ErrorEntry.Unknown
}

fun ErrorEntry.toDomain(): Error = when (this) {
    is ErrorEntry.Custom -> Error.Custom(
        title = title,
        detail = detail
    )

    ErrorEntry.NullParams -> Error.NullParams
    else -> Error.Unknown
}