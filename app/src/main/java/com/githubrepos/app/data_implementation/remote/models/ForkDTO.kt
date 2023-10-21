package com.githubrepos.app.data_implementation.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class ForkDTO(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "node_id") val nodeId: String,
    @field:Json(name = "full_name") val fullName: String
)