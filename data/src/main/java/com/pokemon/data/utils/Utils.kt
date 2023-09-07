package com.pokemon.data.utils

import com.pokemon.data.BuildConfig
import com.pokemon.data.model.Ability
import com.pokemon.data.model.AbilityModel
import com.pokemon.data.model.ModelPokemon
import com.pokemon.data.model.PokemonModel

private fun String.customToInt(url: String): Int =
    this.replace(url, "", false)
        .replace("/", "", false)
        .toInt()

fun List<Ability>.toAbilityModel(): List<AbilityModel> = this.map { ability ->
    AbilityModel(
        name = ability.ability.name,
        id = ability.ability.url.customToInt(
            "https://pokeapi.co/api/v2/ability/"
        )
    )
}

fun List<ModelPokemon>.toPokemonModel(): List<PokemonModel> = this.map { modelPokemon ->
    PokemonModel(
        modelPokemon.name,
        modelPokemon.url,
        modelPokemon.url.customToInt(
            BuildConfig.BASE_URL + "pokemon"
        )
    )
}