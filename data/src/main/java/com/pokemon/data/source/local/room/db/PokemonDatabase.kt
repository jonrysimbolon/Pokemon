package com.pokemon.data.source.local.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pokemon.data.model.PokemonModel
import com.pokemon.data.source.local.room.PokemonDao

const val DATABASE_NAME = "PokemonDb"
const val CURRENT_VERSION = 1

@Database(
    entities = [PokemonModel::class],
    version = CURRENT_VERSION,
    exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}
