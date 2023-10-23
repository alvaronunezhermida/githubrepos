package com.githubrepos.app.di

import android.app.Application
import androidx.room.Room
import com.githubrepos.app.data_implementation.local.Database
import com.githubrepos.app.data_implementation.local.RoomDataSource
import com.githubrepos.app.data_implementation.remote.GithubApi
import com.githubrepos.app.data_implementation.remote.clients.Client
import com.githubrepos.app.navigation.AppNavigator
import com.githubrepos.data.source.LocalDataSource
import com.githubrepos.data.source.RemoteDataSource
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
        Database::class.java
    ).build()

    @Provides
    @Singleton
    fun provideRepoDao(db: Database) = db.repoDao()

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
    fun provideReposApi(@ApiUrl apiUrl: String, okHttpClient: OkHttpClient): GithubApi {
        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideReposLocalDataSource(roomDataSource: RoomDataSource): LocalDataSource =
        roomDataSource

    @Provides
    @Singleton
    fun provideReposRemoteDataSource(client: Client): RemoteDataSource =
        client
}