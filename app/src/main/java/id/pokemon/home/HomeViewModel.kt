package id.pokemon.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jonrysimbolon.base.viewmodel.BaseViewModel
import com.pokemon.data.model.DetailPokemonModel
import com.pokemon.data.model.PokemonModel
import com.pokemon.data.repository.pokemon.PokemonRepository
import com.pokemon.data.utils.OrderBy
import com.pokemon.data.utils.ResultStatus
import id.pokemon.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val pokemonRepository: PokemonRepository
) : BaseViewModel() {

    val defaultOption = OrderBy.ASC

    private val _pokemons = MutableStateFlow<ResultStatus<List<PokemonModel>>>(ResultStatus.Loading)
    val pokemons: StateFlow<ResultStatus<List<PokemonModel>>>
        get() = _pokemons

    private val _detailPokemon : MutableLiveData<Event<ResultStatus<DetailPokemonModel>>> = MutableLiveData()
    val detailPokemon: LiveData<Event<ResultStatus<DetailPokemonModel>>>
        get() = _detailPokemon


    init {
        getAllPokemon(defaultOption)
    }

    fun getAllPokemon(orderBy: OrderBy) {
        viewModelScope.launch {
            pokemonRepository.getAllPokemon(orderBy)
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    e.printStackTrace()
                    _pokemons.value = ResultStatus.Error(e.message.toString())
                }
                .collect { pokemons ->
                    _pokemons.value = pokemons
                }
        }
    }

    fun getPokemon(name: String) {
        viewModelScope.launch {
            pokemonRepository.getPokemon(0, name)
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    e.printStackTrace()
                    _detailPokemon.value = Event(ResultStatus.Error(e.message.toString()))
                }
                .collect { pokemon ->
                    _detailPokemon.value = Event(pokemon)
                }
        }
    }
}