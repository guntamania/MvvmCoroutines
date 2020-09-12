package com.guntamania.mvvm_coroutines.entities

import com.squareup.moshi.Json

data class Issue (
    var id: Long,
    val title: String,
    val body: String,
    val url: String,
    @Json(name = "created_at") val createdAt: String
)