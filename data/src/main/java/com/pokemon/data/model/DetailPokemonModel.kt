package com.pokemon.data.model

import com.google.gson.annotations.SerializedName
import com.jonrysimbolon.base.model.BaseModel

data class DetailPokemonModel(
    val abilities: List<Ability> = listOf(),
    val sprites: Sprites,
    val name: String = "",
    val id: Int = 0,
)

data class Ability(
    val ability: AbilityX,
)

data class AbilityX(
    val name: String = "",
    val url: String = "",
)

data class Sprites(
    @SerializedName("back_default")
    val backDefault: String = "",
    @SerializedName("front_default")
    val frontDefault: String = "",
)

data class AbilityModel(
    override val id: Int = 0,
    val name: String = "",
): BaseModel<Int>