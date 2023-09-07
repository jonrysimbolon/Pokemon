package id.pokemon

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PokemonApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PokemonApp)
            modules(
                remoteModule(BuildConfig.BASE_URL),
                localModule,
                repositoryModule,
                adapterModule,
                viewModelModule,
            )
        }
    }
}