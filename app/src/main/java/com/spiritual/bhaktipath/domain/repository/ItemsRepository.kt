package com.spiritual.bhaktipath.domain.repository

import com.spiritual.bhaktipath.utils.ItemsData

interface ItemsRepository {

    fun getItems(): ItemsData
}