package com.example.up.model

import kotlinx.serialization.Serializable

@Serializable
data class notifications(
    val id: String,
    val created_at: Long,
    val text: String,
)