package com.githubrepos.domain

sealed class Error {
    data class Custom(
        val title: String,
        val detail: String
    ) : Error()

    object NullParams : Error()
    object Unknown : Error()
    object NullBody : Error()
}