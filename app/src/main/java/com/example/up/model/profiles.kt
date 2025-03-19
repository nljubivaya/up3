package com.example.up.model

import kotlinx.serialization.Serializable

@Serializable
data class profiles(
    val id: String = "",
    val user_id: String = "",
    val photo: String = "",
    val firstname: String = "",
    val lastname: String = "",
    val address: String = "",
    val phone: String = "",
    val email: String = "",
    val name: String = "",
    val password: String = "",
)