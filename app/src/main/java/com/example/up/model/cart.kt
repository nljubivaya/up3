package com.example.up.model

import kotlinx.serialization.Serializable

@Serializable
data class cart(
    val id: String,
    val product_id: String,
    val user_id: String,
    val count: Int,
)