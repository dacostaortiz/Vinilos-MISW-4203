package com.app.vinilos_misw4203.models

data class Performer(
    val performerId: Int,
    val name: String,
    val image: String,
    val description: String,
    val performerType: String,
    val birthDate: String? = null
)
