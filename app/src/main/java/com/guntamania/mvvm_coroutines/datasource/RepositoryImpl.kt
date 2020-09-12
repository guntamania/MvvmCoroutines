package com.guntamania.mvvm_coroutines.datasource

import android.accounts.NetworkErrorException
import com.guntamania.mvvm_coroutines.entities.Issue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.Response

class RepositoryImpl(
    private val apiClient: ApiClient
) : Repository {

    override suspend fun getIssues(scope: CoroutineScope, page: Int) = scope.async {
        val result: Response<List<Issue>> =
            apiClient.getClient().create(IssueApi::class.java).getIssues(page.toString())
        val body = result.body() ?: throw NullPointerException()
        if (!result.isSuccessful) throw NetworkErrorException("Error: $result.body()")
        body
    }

    override suspend fun getIssues1(page: Int): List<Issue> {
        val result: Response<List<Issue>> =
            apiClient.getClient().create(IssueApi::class.java).getIssues(page.toString())
        val body = result.body() ?: throw NullPointerException()
        if (!result.isSuccessful) throw NetworkErrorException("Error: $result.body()")
        return body
    }

    override suspend fun getIssues2(page: Int) = flow {
        emit(apiClient.getClient().create(IssueApi::class.java).getIssues1(page.toString()))
    }.flowOn(Dispatchers.IO)

}