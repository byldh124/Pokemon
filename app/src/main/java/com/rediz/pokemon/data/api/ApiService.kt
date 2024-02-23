package com.rediz.pokemon.data.api

import com.rediz.pokemon.data.model.PokeDetailResponse
import com.rediz.pokemon.data.model.PokeListResponse
import com.rediz.pokemon.data.model.PokeSpeciesResponse
import com.rediz.pokemon.domain.model.PokeDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon")
    suspend fun getPokeList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): PokeListResponse

    @GET("pokemon/{index}")
    suspend fun getPokeDetail(@Path("index") index: Int): PokeDetailResponse

    @GET("pokemon-species/{index}")
    suspend fun getPokeName(@Path("index") index: Int): PokeSpeciesResponse
}