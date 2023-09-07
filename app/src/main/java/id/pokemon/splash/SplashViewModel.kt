package id.pokemon.splash

import androidx.annotation.AnimRes
import com.jonrysimbolon.base.viewmodel.BaseViewModel
import id.pokemon.R

class SplashViewModel : BaseViewModel() {
    @AnimRes
    val animateSplashImage = R.anim.side_slide
    val toHomeFragment = R.id.action_splashFragment_to_homeFragment
}