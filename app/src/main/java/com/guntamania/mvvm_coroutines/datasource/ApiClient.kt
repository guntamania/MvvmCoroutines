package com.guntamania.mvvm_coroutines.datasource

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiClient {
    fun getClient(): Retrofit = retrofit()

    private fun retrofit() = Retrofit.Builder()
        .addConverterFactory(createConverter())
        .baseUrl("https://api.github.com")
        .client(createOkHttpClient())
        .build()

    private fun createOkHttpClient(): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private fun createConverter() = MoshiConverterFactory.create(
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    )
}