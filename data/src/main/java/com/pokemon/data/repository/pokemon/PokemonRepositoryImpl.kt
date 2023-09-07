package com.pokemon.data.repository.pokemon

import com.pokemon.data.model.DetailPokemonModel
import com.pokemon.data.model.PokemonModel
import com.pokemon.data.source.local.room.PokemonDao
import com.pokemon.data.source.remote.retrofit.PokemonService
import com.pokemon.data.utils.OrderBy
import com.pokemon.data.utils.ResultStatus
import com.pokemon.data.utils.toPokemonModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokemonRepositoryImpl(
    private val remote: PokemonService,
    private val local: PokemonDao
) : PokemonRepository {
    override suspend fun getAllPokemon(order: OrderBy): Flow<ResultStatus<List<PokemonModel>>> = flow {
        emit(ResultStatus.Loading)
        val remoteData = remote.getAllPokemon().results
        local.insertUsers(
            remoteData.toPokemonModel()
        )
        try {
            emit(ResultStatus.Success(local.getListPokemonOrder(order.name)))
        }catch (e: Exception) {
            e.printStackTrace()
            emit(ResultStatus.Error(e.message.toString()))
        }
    }

    override suspend fun getPokemon(id: Int, name: String): Flow<ResultStatus<DetailPokemonModel>> = flow {
        emit(ResultStatus.Loading)
        try{
            if(id != 0){
                emit(ResultStatus.Success(remote.getDetailPokemonById(id)))
            }else{
                emit(ResultStatus.Success(remote.getDetailPokemonByName(name)))
            }
        }catch (e: Exception){
            e.printStackTrace()
            emit(ResultStatus.Error(e.message.toString()))
        }
    }
}
