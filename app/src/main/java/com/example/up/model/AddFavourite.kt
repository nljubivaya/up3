package com.example.up.model

import kotlinx.serialization.Serializable

@Serializable
data class AddFavourite(
    val product_id: String,
    val user_id: String,
)