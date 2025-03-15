package com.example.up.model

import kotlinx.serialization.Serializable

@Serializable
data class orders_items(
    val id: String,
    val created_at: Long,
    val title: String,
    val coast: Float,
    val count: Int,
    val order_id: Int,
    val product_id: String,

)