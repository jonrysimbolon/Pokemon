package com.pokemon.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pokemon.data.model.NAME_FIELD
import com.pokemon.data.model.POKEMON_TBL_NAME
import com.pokemon.data.model.PokemonModel

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(user: List<PokemonModel>)

    @Query("SELECT * FROM $POKEMON_TBL_NAME ORDER BY " +
            "CASE WHEN :orderBy = 'ASC' THEN $NAME_FIELD END ASC, " +
                    "CASE WHEN :orderBy = 'DESC' THEN $NAME_FIELD END DESC")
    suspend fun getListPokemonOrder(orderBy: String): List<PokemonModel>

}