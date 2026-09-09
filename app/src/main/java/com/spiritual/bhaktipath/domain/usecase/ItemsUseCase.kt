package com.spiritual.bhaktipath.domain.usecase

import com.spiritual.bhaktipath.domain.repository.ItemsRepository
import com.spiritual.bhaktipath.utils.ItemsData
import javax.inject.Inject

class ItemsUseCase @Inject constructor(private val repository: ItemsRepository) {

    suspend operator fun invoke(): List<ItemsData> {
        return repository.getItems()
    }
}