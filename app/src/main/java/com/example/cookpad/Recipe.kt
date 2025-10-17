// Recipe.kt
package com.example.cookpad

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val title: String,
    @DrawableRes val image: Int,
    val ingredients: List<String>,
    val measurements: List<String>,
    val steps: List<String>
) : Parcelable