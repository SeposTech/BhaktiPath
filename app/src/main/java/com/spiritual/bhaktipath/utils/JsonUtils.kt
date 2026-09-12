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

    fun readDetails(
        context: Context,
        @RawRes resourceId: Int
    ): ItemDetail {

        val json = context.resources
            .openRawResource(resourceId)
            .bufferedReader()
            .use { it.readText() }

        return gson.fromJson(
            json,
            ItemDetail::class.java
        )
    }
}



data class ItemsData(
    val chalisa: List<Item> = emptyList(),
    val aarti: List<Item> = emptyList(),
    val mantra: List<Item> = emptyList()
)

data class Item(
    val id: Int,
    val title: String
)

data class ItemDetail(
    val data: List<ItemDetailData?> = emptyList()
)

data class ItemDetailData(
    val id: Int,
    val title: String,
    val content: String
)