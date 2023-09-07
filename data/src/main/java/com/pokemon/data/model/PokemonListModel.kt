package com.pokemon.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.jonrysimbolon.base.model.BaseModel

const val POKEMON_TBL_NAME = "PokemonTbl"
const val NAME_FIELD = "name"
const val URL_FIELD = "url"
const val ID_FIELD = "id"

data class PokemonListModel(
    @field:SerializedName("count")
    val count: Int,
    @field:SerializedName("name")
    val next: String,
    @field:SerializedName("previous")
    val previous: String?,
    @field:SerializedName("results")
    val results: List<ModelPokemon>
)

data class ModelPokemon(
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("url")
    val url: String,
)

@Entity(tableName = POKEMON_TBL_NAME)
data class PokemonModel(
    @field:ColumnInfo(name = NAME_FIELD)
    val name: String = "",
    @field:ColumnInfo(name = URL_FIELD)
    val url: String = "",
    @field:ColumnInfo(name = ID_FIELD)
    @field:PrimaryKey
    override val id: Int = 0
) : BaseModel<Int>

