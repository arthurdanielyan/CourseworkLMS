package com.coursework.data.bookDetails.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NamedItemResponse(
    @SerialName("id") val id: Int,
    @SerialName("display_name") val displayName: String,
)
