package com.rediz.pokemon.domain.repository

import androidx.paging.PagingData
import com.rediz.pokemon.domain.model.PokeDetail
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getList(): Flow<PagingData<PokeDetail>>
}