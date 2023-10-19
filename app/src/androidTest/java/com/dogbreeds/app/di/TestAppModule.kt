package com.githubrepos.app.di

import android.app.Application
import androidx.room.Room
import com.githubrepos.app.data_implementation.local.BreedDatabase
import com.githubrepos.app.data_implementation.local.BreedRoomDataSource
import com.githubrepos.app.data_implementation.remote.BreedsApi
import com.githubrepos.app.data_implementation.remote.clients.BreedsClient
import com.githubrepos.app.navigation.AppNavigator
import com.githubrepos.data.source.BreedsLocalDataSource
import com.githubrepos.data.source.BreedsRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object TestAppModule {

    @Provides
    @Singleton
    fun provideAppNavigator(): AppNavigator {
        return AppNavigator()
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.inMemoryDatabaseBuilder(
        app,
        BreedDatabase::class.java
    ).build()

    @Provides
    @Singleton
    fun provideBreedDao(db: BreedDatabase) = db.breedDao()

    @Provides
    @Singleton
    @ApiUrl
    fun provideApiUrl(): String = "http://localhost:8080"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(this).build()
    }

    @Provides
    @Singleton
    fun provideBreedsApi(@ApiUrl apiUrl: String, okHttpClient: OkHttpClient): BreedsApi {
        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideBreedsLocalDataSource(breedsRoomDataSource: BreedRoomDataSource): BreedsLocalDataSource =
        breedsRoomDataSource

    @Provides
    @Singleton
    fun provideBreedsRemoteDataSource(breedsClient: BreedsClient): BreedsRemoteDataSource =
        breedsClient
}