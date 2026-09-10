package com.spiritual.bhaktipath.domain.repository

import com.spiritual.bhaktipath.utils.ItemDetail

interface DetailRepository {

    fun getItemDetail(itemId: Int): ItemDetail?
}