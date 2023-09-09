package id.pokemon.home

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.jonrysimbolon.base.fragment.BaseFragment
import com.pokemon.data.utils.OrderBy
import com.pokemon.data.utils.ResultStatus
import id.pokemon.R
import id.pokemon.adapter.HomeAdapter
import id.pokemon.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(
        FragmentHomeBinding::inflate
    ) {

    private val homeAdapter: HomeAdapter by inject()
    override val baseViewModel: HomeViewModel by viewModel()

    override fun setUpUi(savedInstanceState: Bundle?) {
        super.setUpUi(savedInstanceState)

        if(savedInstanceState === null){
            baseViewModel.getAllPokemon()
        }

        binding.apply {
            pokemonRecyclerView.adapter = homeAdapter
            homeAdapter.onClickItem = { view, data ->
                val toDetailFragment =
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                        data.name,
                        data.id
                    )
                view.findNavController().navigate(toDetailFragment)
            }

            toolbar.apply {

                setOnClickListener {
                    baseViewModel.getAllPokemon(OrderBy.ASC)
                }

                baseViewModel.searchConfig(
                    requireActivity(),
                    requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager,
                    menu.findItem(R.id.search).actionView as SearchView
                )

                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.sortAsc -> {
                            baseViewModel.getAllPokemon(OrderBy.ASC)
                            true
                        }

                        R.id.sortDesc -> {
                            baseViewModel.getAllPokemon(OrderBy.DESC)
                            true
                        }

                        else -> {
                            false
                        }
                    }
                }
            }
        }
    }

    override fun setUpVm() {
        super.setUpVm()
        baseViewModel.detailPokemon.observe(viewLifecycleOwner) { value ->
            value.getContentIfNotHandled()?.let { result ->
                when (result) {
                    ResultStatus.Loading -> {
                        showLoading()
                    }

                    is ResultStatus.Error -> {
                        hideLoading()
                        showErrorDismiss(result.error)
                    }

                    is ResultStatus.Success -> {
                        hideLoading()
                        val data = result.data
                        Timber.tag(TAG).d(data.name)
                        val toDetailFragment =
                            HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                                data.name,
                                data.id
                            )
                        findNavController().navigate(toDetailFragment)
                    }
                }
            }
        }

        baseViewModel.pokemons
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { result ->
                when (result) {
                    ResultStatus.Loading -> {
                        showLoading()
                    }

                    is ResultStatus.Error -> {
                        hideLoading()
                        showError(result.error) {
                            baseViewModel.getAllPokemon()
                        }
                    }

                    is ResultStatus.Success -> {
                        hideLoading()
                        val data = result.data
                        data.forEach { pokemonModel ->
                            Timber.d(pokemonModel.name)
                        }
                        homeAdapter.updateData(data)
                    }
                }
            }
            .launchIn(lifecycleScope)
    }

    companion object{
        const val TAG = "HomeFragment"
    }
}