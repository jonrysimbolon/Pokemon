package com.pokemon.data.source.remote.retrofit

import com.pokemon.data.model.DetailPokemonModel
import com.pokemon.data.model.PokemonListModel
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {

    @GET("pokemon")
    suspend fun getAllPokemon(): PokemonListModel

    @GET("pokemon/{id}")
    suspend fun getDetailPokemonById(@Path("id") data: Int): DetailPokemonModel

    @GET("pokemon/{name}")
    suspend fun getDetailPokemonByName(@Path("name") name: String): DetailPokemonModel
}