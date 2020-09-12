package com.guntamania.mvvm_coroutines.datasource

import com.guntamania.mvvm_coroutines.entities.Issue
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IssueApi {
    @GET("repos/bitcoin/bitcoin/issues")
    suspend fun getIssues(
        @Query("page") page: String,
        @Query("per_page") perPage: String = "10"
    ): Response<List<Issue>>

    @GET("repos/bitcoin/bitcoin/issues")
    suspend fun getIssues1(
        @Query("page") page: String,
        @Query("per_page") perPage: String = "10"
    ): List<Issue>

    @GET("repos/bitcoin/bitcoin/issues")
    suspend fun getIssues2(
        @Query("page") page: String,
        @Query("per_page") perPage: String = "10"
    ): Response<List<Issue>>
}