package com.cumaliguzel.apps.data
data class Clothes(
    val id: Int = 0, // Mevcut id alanı korunuyor
    val gender: String = "",
    val img: String = "",
    val isFavorite: Boolean = false, // Favori durumu
    val bottomLink : String = "",
    val topLink : String = ""
)

