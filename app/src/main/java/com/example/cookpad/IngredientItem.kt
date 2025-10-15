package com.example.cookpad

data class IngredientItem(
    val name: String,
    val measurement: String,
    var isChecked: Boolean = false
)
