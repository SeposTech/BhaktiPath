package com.spiritual.bhaktipath.di

import com.spiritual.bhaktipath.data.repository.DetailRepositoryImp
import com.spiritual.bhaktipath.data.repository.ItemsRepositoryImpl
import com.spiritual.bhaktipath.domain.repository.DetailRepository
import com.spiritual.bhaktipath.domain.repository.ItemsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindItemsRepository(
        repositoryImpl: ItemsRepositoryImpl
    ): ItemsRepository

    @Binds
    @Singleton
    abstract fun bindDetailRepository(
        repositoryImpl: DetailRepositoryImp
    ): DetailRepository

}