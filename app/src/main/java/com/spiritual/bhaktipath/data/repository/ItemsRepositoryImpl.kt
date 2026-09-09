package com.spiritual.bhaktipath.data.repository

import android.content.Context
import android.util.Log
import com.spiritual.bhaktipath.R
import com.spiritual.bhaktipath.domain.repository.ItemsRepository
import com.spiritual.bhaktipath.utils.ItemsData
import com.spiritual.bhaktipath.utils.JsonUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ItemsRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context) :
    ItemsRepository {

    override fun getItems(): List<ItemsData> {
        return try {
            val items = JsonUtils.readItems(
                context = context,
                resourceId = R.raw.items
            )
            listOf(items)
        } catch (e: Exception) {
            Log.e("ItemsRepositoryImpl", "Failed to load items", e)
            emptyList()
        }
    }
}