package com.aspire.sawa.models

import java.util.*

data class Place(
    val id: String,
    val name: String,
    val distance: String,
    val image: Int,
    val opened: String,
    val capacity: String,
    val capacityState: String,
    val branch: String
)