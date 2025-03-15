package com.example.up.model

import kotlinx.serialization.Serializable

@Serializable
data class payments(
    val id: String,
    val created_at: Long,
    val user_id: String,
    val card_name: String,
    val card_number: Int,
)