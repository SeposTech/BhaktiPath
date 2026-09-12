package com.spiritual.bhaktipath.data.repository

import android.content.Context
import com.spiritual.bhaktipath.R
import com.spiritual.bhaktipath.domain.repository.DetailRepository
import com.spiritual.bhaktipath.utils.ItemDetailData
import com.spiritual.bhaktipath.utils.JsonUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject

class DetailRepositoryImp @Inject constructor(@ApplicationContext private val context: Context) :
    DetailRepository {

    override fun getItemDetail(itemId: Int): ItemDetailData? {
        return try {
            val response = JsonUtils.readDetails(
                context = context,
                resourceId = R.raw.details
            )

          return  response.data.find { it?.id == itemId }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}