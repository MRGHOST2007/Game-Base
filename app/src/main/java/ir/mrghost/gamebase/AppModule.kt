package ir.mrghost.gamebase

import android.content.Context
import ir.mrghost.gamebase.data.local.FakeGameRepository
import ir.mrghost.gamebase.data.local.GameBaseDatabase
import ir.mrghost.gamebase.data.local.GameRepository
import ir.mrghost.gamebase.data.local.favorites.FavoritesRepository
import ir.mrghost.gamebase.data.remote.GameBladeApi
import ir.mrghost.gamebase.data.remote.GameBladeRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {
    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    private val database by lazy {
        GameBaseDatabase.getInstance(appContext)
    }

    private val gameRepository: FakeGameRepository by lazy {
        FakeGameRepository()
    }

    private val favoritesRepository: FavoritesRepository by lazy {
        FavoritesRepository(database.favoriteDao())
    }

    fun provideGameRepository(): GameRepository = gameRepository
    fun provideFavoritesRepository(): FavoritesRepository = favoritesRepository

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    private fun provideGameBladeRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://gameblade.ir/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .build()
    }

    fun provideGameBladeApi(): GameBladeApi =
        provideGameBladeRetrofit().create(GameBladeApi::class.java)

    fun provideGameBladeRepository(): GameBladeRepository =
        GameBladeRepository(provideGameBladeApi())
}