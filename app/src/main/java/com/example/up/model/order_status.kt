package com.example.up.model

import kotlinx.serialization.Serializable


@Serializable
data class order_status(
    val id: String,
    val created_at: Long,
    val name: String,
)