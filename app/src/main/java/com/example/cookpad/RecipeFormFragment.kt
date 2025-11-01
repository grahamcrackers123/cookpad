package com.example.cookpad

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout

class RecipeFormFragment: Fragment(R.layout.fragment_recipe_form), FabController {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<MaterialToolbar>(R.id.recipeFormTopAppBar)

        toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val saveButton = view.findViewById<MaterialButton>(R.id.saveRecipeButton)

        saveButton.setOnClickListener {
            if (validateInputs(view)) {
                parentFragmentManager.popBackStack()
            }

        }

        val cancelButton = view.findViewById<MaterialButton>(R.id.cancelRecipeButton)

        cancelButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun validateInputs(view: View): Boolean {
        val nameLayout = view.findViewById<TextInputLayout>(R.id.nameTextField)
        val categoryLayout = view.findViewById<TextInputLayout>(R.id.categoryTextField)
        val ingredientLayout = view.findViewById<TextInputLayout>(R.id.ingredientTextField)
        val measurementLayout = view.findViewById<TextInputLayout>(R.id.measurementTextField)
        val stepsLayout = view.findViewById<TextInputLayout>(R.id.stepTextField)

        val name = nameLayout.editText?.text.toString().trim()
        val category = categoryLayout.editText?.text.toString().trim()
        val ingredient = ingredientLayout.editText?.text.toString().trim()
        val measurement = measurementLayout.editText?.text.toString().trim()
        val steps = stepsLayout.editText?.text.toString().trim()

        nameLayout.error = null
        categoryLayout.error = null
        ingredientLayout.error = null
        measurementLayout.error = null
        stepsLayout.error = null

        var isValid = true

        if (name.isEmpty()) {
            nameLayout.error = "Recipe name cannot be empty"
            isValid = false
        }
        if (category.isEmpty()) {
            categoryLayout.error = "Category cannot be empty"
            isValid = false
        }
        if (ingredient.isEmpty()) {
            ingredientLayout.error = "Ingredient cannot be empty"
            isValid = false
        }
        if (measurement.isEmpty()) {
            measurementLayout.error = "Measurement cannot be empty"
            isValid = false
        }
        if (steps.isEmpty()) {
            stepsLayout.error = "Steps cannot be empty"
            isValid = false
        }
        return isValid
    }

    override fun showFab(): Boolean {
        return false
    }

    override fun setupFab(fab: FloatingActionButton) {
        fab.setOnClickListener(null)
    }
}