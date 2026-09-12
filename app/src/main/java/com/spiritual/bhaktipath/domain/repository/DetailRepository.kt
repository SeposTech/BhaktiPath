package com.spiritual.bhaktipath.domain.repository

import com.spiritual.bhaktipath.utils.ItemDetailData

interface DetailRepository {

    fun getItemDetail(itemId: Int): ItemDetailData?
}