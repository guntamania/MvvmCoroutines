package com.guntamania.mvvm_coroutines.datasource

import com.guntamania.mvvm_coroutines.entities.Issue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface Repository {
    suspend fun getIssues(scope: CoroutineScope, page: Int): Deferred<List<Issue>>
    suspend fun getIssues1(page: Int): List<Issue>
    suspend fun getIssues2(page: Int): Flow<List<Issue>>
}