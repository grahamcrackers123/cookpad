package com.example.cookpad

import androidx.annotation.DrawableRes

data class Category(
    val name: String,
    @DrawableRes val image: Int,
    val previewRecipeNames: List<String> = emptyList()
)
