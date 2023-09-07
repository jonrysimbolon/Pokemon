package id.pokemon.detail

import androidx.lifecycle.viewModelScope
import com.jonrysimbolon.base.viewmodel.BaseViewModel
import com.pokemon.data.model.DetailPokemonModel
import com.pokemon.data.repository.pokemon.PokemonRepository
import com.pokemon.data.utils.ResultStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class DetailViewModel(
    private var pokemonRepository: PokemonRepository
) : BaseViewModel() {

    private val _detailPokemon = MutableStateFlow<ResultStatus<DetailPokemonModel>>(ResultStatus.Loading)
    val detailPokemon: StateFlow<ResultStatus<DetailPokemonModel>>
        get() = _detailPokemon


    fun getPokemon(id: Int){
        viewModelScope.launch {
            pokemonRepository.getPokemon(id, "")
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    e.printStackTrace()
                    _detailPokemon.value = ResultStatus.Error(e.message.toString())
                }
                .collect { pokemon ->
                    _detailPokemon.value = pokemon
                }
        }
    }
}