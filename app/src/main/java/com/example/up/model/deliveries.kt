package com.example.up.model

import kotlinx.serialization.Serializable
import java.security.Timestamp

@Serializable
data class deliveries(
    val id: String,
    val created_at: Long,
    val city: String,
)