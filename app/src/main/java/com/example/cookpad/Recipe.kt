package com.example.cookpad

import androidx.annotation.DrawableRes

data class Recipe(
    val name: String,
    @DrawableRes val image: Int
)
