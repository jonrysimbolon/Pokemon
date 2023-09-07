package id.pokemon

import androidx.room.Room
import com.pokemon.data.repository.pokemon.PokemonRepository
import com.pokemon.data.repository.pokemon.PokemonRepositoryImpl
import com.pokemon.data.source.local.room.db.DATABASE_NAME
import com.pokemon.data.source.local.room.db.PokemonDatabase
import com.pokemon.data.source.remote.retrofit.PokemonService
import id.pokemon.adapter.DetailAdapter
import id.pokemon.adapter.HomeAdapter
import id.pokemon.detail.DetailViewModel
import id.pokemon.home.HomeViewModel
import id.pokemon.splash.SplashViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val loggingInterceptor = with(HttpLoggingInterceptor()) {
    if (BuildConfig.DEBUG)
        setLevel(HttpLoggingInterceptor.Level.BODY)
    else
        setLevel(HttpLoggingInterceptor.Level.NONE)
}

private val client = with(OkHttpClient.Builder()) {
    addInterceptor(loggingInterceptor)
    build()
}

fun retrofit(url: String): Retrofit = with(Retrofit.Builder()) {
    baseUrl(url)
    addConverterFactory(GsonConverterFactory.create())
    client(client)
    build()
}

fun remoteModule(url: String) = module {
    single { retrofit(url).create(PokemonService::class.java) }
}

val localModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            PokemonDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    single {
        get<PokemonDatabase>().pokemonDao()
    }
}

val repositoryModule = module {
    single<PokemonRepository> {
        PokemonRepositoryImpl(
            get(),
            get()
        )
    }

}

val adapterModule = module {
    factory {
        HomeAdapter()
    }
    factory {
        DetailAdapter()
    }
}

val viewModelModule = module {
    viewModelOf(::SplashViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailViewModel)
}
