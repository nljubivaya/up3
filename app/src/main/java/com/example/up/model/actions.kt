package com.example.up.model

import kotlinx.serialization.Serializable
import java.security.Timestamp

@Serializable
data class actions(
    val id: String,
    val created_at: Long,
    val photo: String,
)