package com.spiritual.bhaktipath.data.repository

import android.content.Context
import com.spiritual.bhaktipath.R
import com.spiritual.bhaktipath.domain.repository.DetailRepository
import com.spiritual.bhaktipath.utils.ItemDetail
import com.spiritual.bhaktipath.utils.JsonUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject

class DetailRepositoryImp @Inject constructor(@ApplicationContext private val context: Context) :
    DetailRepository {
    override fun getItemDetail(itemId: Int): ItemDetail? {
        return try {
            val response = JsonUtils.readItems(
                context = context,
                resourceId = R.raw.details
            )

            val allItems = response.chalisa + response.aarti + response.mantra
            val foundItem = allItems.find { it.id == itemId }

            foundItem?.let {
                ItemDetail(
                    id = it.id,
                    content = it.title
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}