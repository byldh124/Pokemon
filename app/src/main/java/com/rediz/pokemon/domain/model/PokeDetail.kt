package com.rediz.pokemon.domain.model

data class PokeDetail(
    val index: Int = 0,
    var name: String = "",
    var url: String = "",
    var type: List<String> = emptyList(),
    var imageUrl: String = "",
)
