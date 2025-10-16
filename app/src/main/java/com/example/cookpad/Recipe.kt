// Recipe.kt
package com.example.cookpad

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize // Requires the 'kotlin-parcelize' plugin in your build.gradle (app level)
data class Recipe(
    val title: String,
    @DrawableRes val image: Int,
    val ingredients: List<String>,
    val steps: List<String>
) : Parcelable