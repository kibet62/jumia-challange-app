package com.example.jumiachallange.model.configarations

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Support(
    @SerialName("phone_number")
    val phoneNumber: String,
    @SerialName("call_to_order_enabled")
    val callToOrderEnabled: Boolean,
    @SerialName("cs_email")
    val csEmail: String
)