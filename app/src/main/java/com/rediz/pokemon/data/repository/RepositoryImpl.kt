package com.rediz.pokemon.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rediz.pokemon.data.source.PagingSource
import com.rediz.pokemon.domain.model.PokeDetail
import com.rediz.pokemon.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    val pagingSource: PagingSource
): Repository {
    override suspend fun getList(): Flow<PagingData<PokeDetail>> {
        return Pager(
            config = PagingConfig(10),
            pagingSourceFactory = { pagingSource }
        ).flow
    }

}