package com.githubrepos.app.data_implementation.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class BreedImagesResponseDTO(
    @field:Json(name = "message") val breedImages: List<String>? = null,
    @field:Json(name = "status") val status: String? = null
)