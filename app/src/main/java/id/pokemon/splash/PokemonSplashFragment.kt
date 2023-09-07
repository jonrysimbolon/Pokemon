package id.pokemon.splash

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.jonrysimbolon.base.fragment.BaseFragment
import id.pokemon.databinding.FragmentSplashBinding
import org.koin.android.ext.android.inject

class PokemonSplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(
    FragmentSplashBinding::inflate
) {
    
    override val baseViewModel: SplashViewModel by inject()

    override fun setUpUi(savedInstanceState: Bundle?) {
        super.setUpUi(savedInstanceState)
        binding.apply {
            titleText.startAnimation()
            splashImage.startAnimation()
            startBtn.startAnimation()
            startBtn.setOnClickListener {
                findNavController().navigate(baseViewModel.toHomeFragment)
            }
        }
    }

    private fun View.startAnimation() {
        this.startAnimation(
            AnimationUtils.loadAnimation(requireContext(), baseViewModel.animateSplashImage)
        )
    }
}