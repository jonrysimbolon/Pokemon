package id.pokemon

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.Forest.plant


class PokemonApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }

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