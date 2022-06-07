package com.app.memorista.domain.models

data class User(
    val id: Long,
    val name: String,
    val photoUri: String? = null
)
