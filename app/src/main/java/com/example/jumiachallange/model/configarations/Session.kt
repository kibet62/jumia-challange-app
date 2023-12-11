package com.example.jumiachallange.model.configarations

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Session(
    @SerialName("id")
    val sessionId: String,
    @SerialName("expire")
    val expire: String?,
    @SerialName("YII_CSRF_TOKEN")
    val yIICSRFTOKEN: String
)