package com.githubrepos.app.helpers

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.json.JSONObject

class JsonConverterHelper {

    inline fun <reified T> fromJson(json: String): T? = try {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(T::class.java, T::class.java)
        val adapter = moshi.adapter<T>(type)
        adapter.fromJson(json)
    } catch (e: Exception) {
        null
    }

    inline fun <reified T> fromListJson(json: String): List<T>? = try {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, T::class.java)
        val adapter = moshi.adapter<List<T>>(type)
        adapter.fromJson(json)
    } catch (e: Exception) {
        null
    }

    inline fun <reified T> toJson(value: T?): String? = try {
        val moshi: Moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(T::class.java, T::class.java)
        val adapter = moshi.adapter<T>(type)
        adapter.toJson(value)
    } catch (e: Exception) {
        null
    }

    inline fun <reified T> toListJson(value: List<T>?): String? = try {
        val moshi: Moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, T::class.java)
        val adapter = moshi.adapter<List<T>>(type)
        adapter.toJson(value)
    } catch (e: Exception) {
        null
    }


    fun fromJsonToMap(obj: JSONObject): Map<String, Any> {
            val map = mutableMapOf<String, Any>()
            val keysItr: Iterator<String> = obj.keys()
            while (keysItr.hasNext()) {
                val key = keysItr.next()
                val value: Any? = obj.get(key)
                value?.apply {
                    map[key] = this
                }
            }
            return map
    }
}

