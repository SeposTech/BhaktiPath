package com.spiritual.bhaktipath.domain.usecase

import com.spiritual.bhaktipath.domain.repository.DetailRepository
import com.spiritual.bhaktipath.utils.ItemDetail
import jakarta.inject.Inject

class DetailUseCase @Inject constructor(
    private val repository: DetailRepository
) {

    suspend operator fun invoke(itemId: Int): ItemDetail? {
        return repository.getItemDetail(itemId)
    }
}