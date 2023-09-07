package com.pokemon.data.repository.pokemon

import com.pokemon.data.model.DetailPokemonModel
import com.pokemon.data.model.PokemonModel
import com.pokemon.data.utils.OrderBy
import com.pokemon.data.utils.ResultStatus
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun getAllPokemon(order: OrderBy): Flow<ResultStatus<List<PokemonModel>>>
    suspend fun getPokemon(id: Int, name: String): Flow<ResultStatus<DetailPokemonModel>>
}