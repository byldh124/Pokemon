package com.rediz.pokemon.data.model

data class PokeSpeciesResponse(
    val names: List<Name>
)

data class Name(
    val language: Language,
    val name: String
)

data class Language(
    val name: String,
    val url: String
)