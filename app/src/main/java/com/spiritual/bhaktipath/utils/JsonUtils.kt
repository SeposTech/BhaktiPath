package com.spiritual.bhaktipath.utils

import android.content.Context
import androidx.annotation.RawRes
import com.google.gson.Gson

object JsonUtils {

    private val gson = Gson()

    fun readItems(
        context: Context,
        @RawRes resourceId: Int
    ): ItemsData {

        val json = context.resources
            .openRawResource(resourceId)
            .bufferedReader()
            .use { it.readText() }

        return gson.fromJson(
            json,
            ItemsData::class.java
        )
    }
}

data class ItemsData(
    val chalisa: List<String> = emptyList(),
    val aarti: List<String> = emptyList(),
    val mantra: List<String> = emptyList()
)