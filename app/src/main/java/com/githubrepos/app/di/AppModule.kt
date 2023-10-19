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
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Dagger Hilt module that provides dependencies for various components of the application.
 *
 * This module is responsible for defining and providing singleton-scoped dependencies used within the application.
 * These dependencies include services, data sources, network clients, and more.
 *
 * @see Module
 * @see InstallIn
 * @see SingletonComponent
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppNavigator(): AppNavigator {
        return AppNavigator()
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.databaseBuilder(
        app,
        BreedDatabase::class.java,
        "breeds-db"
    ).build()

    @Provides
    @Singleton
    fun provideBreedDao(db: BreedDatabase) = db.breedDao()

    @Provides
    @Singleton
    @ApiUrl
    fun provideApiUrl(): String = "https://dog.ceo/api/"

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
            .create(BreedsApi::class.java)
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