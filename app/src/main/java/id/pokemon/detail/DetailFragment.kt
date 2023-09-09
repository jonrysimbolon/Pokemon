package id.pokemon.detail

import android.os.Bundle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jonrysimbolon.base.fragment.BaseFragment
import com.jonrysimbolon.base.setImageUrl
import com.pokemon.data.model.DetailPokemonModel
import com.pokemon.data.utils.ResultStatus
import com.pokemon.data.utils.toAbilityModel
import id.pokemon.R
import id.pokemon.adapter.DetailAdapter
import id.pokemon.databinding.DetailContentScrollingBinding
import id.pokemon.databinding.FragmentDetailBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel>(
    FragmentDetailBinding::inflate
) {
    override val baseViewModel: DetailViewModel by viewModel()
    private var pokemonId: Int = 0
    private var pokemonName: String = ""
    private val detailAdapter: DetailAdapter by inject()
    private lateinit var detContScroll: DetailContentScrollingBinding

    override fun setUpUi(savedInstanceState: Bundle?) {
        super.setUpUi(savedInstanceState)

        detContScroll = DetailContentScrollingBinding.bind(binding.detContScroll.root)
        val bundle = DetailFragmentArgs.fromBundle(arguments as Bundle)
        pokemonId = bundle.id
        pokemonName = bundle.name

        detContScroll.apply {
            abilityRecyclerView.adapter = detailAdapter
        }

        baseViewModel.getPokemon(pokemonId)
        binding.apply {
            toolbar.apply {
                title = pokemonName
                setNavigationOnClickListener {
                    findNavController().popBackStack()
                }
            }
        }
    }

    override fun setUpVm() {
        super.setUpVm()

        baseViewModel.detailPokemon
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { result ->
                when (result) {
                    ResultStatus.Loading -> {
                        showLoading()
                    }

                    is ResultStatus.Error -> {
                        hideLoading()
                        showError(result.error){
                            baseViewModel.getPokemon(pokemonId)
                        }
                    }

                    is ResultStatus.Success -> {
                        hideLoading()
                        val data = result.data
                        populateData(data)
                    }
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun populateData(data: DetailPokemonModel) {
        binding.apply {
            setImageUrl(
                url = data.sprites.frontDefault,
                imageView = ivItemPhoto,
                errorImage = R.drawable.notfound,
                placeHolderImage = R.mipmap.ic_launcher_foreground,
            )
        }
        detContScroll.apply {
            abilityRecyclerView.adapter = detailAdapter
        }

        detContScroll.apply {
            detailAdapter.updateData(data.abilities.toAbilityModel())
        }
    }
}
