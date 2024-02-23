package com.rediz.pokemon.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rediz.pokemon.data.api.ApiService
import com.rediz.pokemon.domain.model.PokeDetail
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PagingSource @Inject constructor(
    private val apiService: ApiService,
) : PagingSource<Int, PokeDetail>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokeDetail> {
        val page = params.key ?: 0
        return try {
            val response = apiService.getPokeList(offset = page * 10, limit = 10)
            val items = response.results
            val details = items.map {
                val paths = it.url.split("/")
                val index = paths[paths.lastIndex -1].toInt()
                val pokeDetail = PokeDetail(index, url = it.url)
                val response2 = apiService.getPokeName(index)
                val name = response2.names.filter { name -> name.language.name == "ko" }.first()
                pokeDetail.name = name.name
                pokeDetail.imageUrl =
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/showdown/$index.gif"
                pokeDetail
            }


            LoadResult.Page(
                data = details,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (items.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokeDetail>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}