package com.githubrepos.app.data_implementation.remote

import com.githubrepos.app.data_implementation.remote.models.RepoDTO
import com.githubrepos.app.data_implementation.remote.models.StargazerDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface GithubApi {

    @GET("repositories")
    suspend fun getAllRepos(): Response<List<RepoDTO>>

    @GET
    suspend fun getStargazers(
        @Url stargazersUrl: String
    ): Response<List<StargazerDTO>>

    @GET
    suspend fun getForks(
        @Url forksUrl: String
    ): Response<List<StargazerDTO>>

    @GET
    suspend fun getLanguages(
        @Url languagesUrl: String
    ): Response<Map<String, Int>>

}