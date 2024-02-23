package com.rediz.pokemon.data.model

data class PokeDetailResponse(
    val types: List<PokeTypes>,
)

data class PokeTypes(
    val type: PokeType,
)

data class PokeType(
    val name: String,
    val url: String,
)