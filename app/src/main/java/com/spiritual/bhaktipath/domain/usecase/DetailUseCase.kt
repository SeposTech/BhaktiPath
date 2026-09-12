package com.spiritual.bhaktipath.domain.usecase

import com.spiritual.bhaktipath.domain.repository.DetailRepository
import com.spiritual.bhaktipath.utils.ItemDetailData
import jakarta.inject.Inject

class DetailUseCase @Inject constructor(
    private val repository: DetailRepository
) {

    suspend operator fun invoke(itemId: Int): ItemDetailData? {
        return repository.getItemDetail(itemId)
    }
}