package com.example.cookpad

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient(
    val id: Int? = null,
    val name: String,
    val amount: String,
    var isChecked: Boolean = false
) : Parcelable
