package com.example.jumiachallange.model.product

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val metadata: Metadata? = null,
    @SerialName("success")
    val success: Boolean
)