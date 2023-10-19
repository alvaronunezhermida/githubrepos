package com.githubrepos.app.data_implementation.remote

import com.githubrepos.app.data_implementation.remote.models.RepoDTO
import retrofit2.Response
import retrofit2.http.GET

interface GithubApi {

    @GET("repositories")
    suspend fun getAllRepos(): Response<List<RepoDTO>>

}