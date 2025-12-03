package com.galih254.potterapp

import java.io.Serializable

data class Book(
    val number: Int?,
    val title: String?,
    val originalTitle: String?,
    val releaseDate: String?,
    val description: String?,
    val pages: Int?,
    val cover: String?
) : Serializable
