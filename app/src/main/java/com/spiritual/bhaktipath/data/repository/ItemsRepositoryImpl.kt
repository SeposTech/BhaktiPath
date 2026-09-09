package com.spiritual.bhaktipath.data.repository

import android.content.Context
import android.util.Log
import com.spiritual.bhaktipath.R
import com.spiritual.bhaktipath.domain.repository.ItemsRepository
import com.spiritual.bhaktipath.utils.ItemsData
import com.spiritual.bhaktipath.utils.JsonUtils

class ItemsRepositoryImpl(private val context: Context) : ItemsRepository {

    override fun getItems(): ItemsData {
        return try {
            JsonUtils.readItems(
                context = context,
                resourceId = R.raw.items
            )
        } catch (e: Exception) {
            Log.e("ItemsRepositoryImpl", "Failed to load items", e)
            ItemsData()
        }
    }
}