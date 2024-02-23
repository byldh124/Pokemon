package com.rediz.pokemon.data.model

import com.rediz.pokemon.domain.model.PokeTemp

data class PokeListResponse(
    val count: Int,
    val results: List<PokeTemp>,
)