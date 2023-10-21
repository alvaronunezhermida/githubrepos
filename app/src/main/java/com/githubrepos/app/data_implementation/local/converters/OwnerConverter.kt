package com.githubrepos.app.data_implementation.local.converters

import androidx.room.TypeConverter
import com.githubrepos.app.data_implementation.local.entities.OwnerEntity
import com.githubrepos.app.helpers.JsonConverterHelper

class OwnerConverter {

    private val jsonConverterHelper: JsonConverterHelper = JsonConverterHelper()

    @TypeConverter
    fun ownerToJson(owner: OwnerEntity): String =
        jsonConverterHelper.toJson(value = owner) ?: ""

    @TypeConverter
    fun jsonToOwner(json: String): OwnerEntity? =
        jsonConverterHelper.fromJson(json = json)
}