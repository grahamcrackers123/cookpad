package com.example.cookpad

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.cookpad.RecipesFragment.Companion.FORM_MODE_KEY
import com.example.cookpad.RecipesFragment.Companion.MODE_NEW_RECIPE
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RecipeFragment : Fragment(R.layout.fragment_recipe), FabController {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipeDetail = arguments?.getParcelable<Recipe>(RecipesFragment.RECIPE_KEY)

        // check if data passed
        if (recipeDetail != null) {

            val toolbar: Toolbar = view.findViewById(R.id.toolbarRecipeDetail)
            val imageView: ImageView = view.findViewById(R.id.recipeDetailImage)
            val titleTextView: TextView = view.findViewById(R.id.recipeDetailTitle)
            val categoryTextView: TextView = view.findViewById(R.id.recipeDetailCategory)
            val ingredientsContainer: LinearLayout = view.findViewById(R.id.ingredientsContainer)
            val stepsContainer: LinearLayout = view.findViewById(R.id.stepsContainer)

            titleTextView.text = recipeDetail.title
            categoryTextView.text = recipeDetail.categories.joinToString(", ")
            toolbar.title = "Recipe Detail"
            Glide.with(this).load(recipeDetail.image).into(imageView)

            val ingredientsMeasurementsCombo: List<String> = recipeDetail.ingredients.zip(recipeDetail.measurements) { ingredient, measurement -> "$measurement $ingredient"
            }

            populateList(ingredientsContainer, ingredientsMeasurementsCombo, isNumbered = false)
            populateList(stepsContainer, recipeDetail.steps, isNumbered = true)

            setupToolbar(toolbar)

        } else {
            Toast.makeText(requireContext(), "Error: Recipe data missing.", Toast.LENGTH_LONG).show()
        }

    }

    private fun setupToolbar(toolbar: Toolbar) {
        // back button
        toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val recipeDetail = arguments?.getParcelable<Recipe>(RecipesFragment.RECIPE_KEY)

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.addToListAction -> {
                    if (recipeDetail != null) {
                        // 1. Create the dialog instance
                        val dialog = AddIngredientsDialog()

                        // 2. Prepare the data to pass to the dialog
                        val ingredientData = recipeDetail.ingredients.zip(recipeDetail.measurements)
                            .mapIndexed { index, pair ->
                                Ingredient(
                                    name = pair.first,
                                    amount = pair.second
                                )
                            }

                        dialog.setAvailableIngredients(ingredientData)

                        dialog.show(parentFragmentManager, "AddIngredientsDialogTag")

                    } else {
                        Toast.makeText(requireContext(), "Error: No recipe data to add.", Toast.LENGTH_SHORT).show()
                    }
                    return@setOnMenuItemClickListener true

                }
                R.id.editAction -> {
                    val bundle = Bundle().apply {
                        putString(FORM_MODE_KEY, MODE_NEW_RECIPE)
                    }

                    val recipeFormFragment = RecipeFormFragment()
                    recipeFormFragment.arguments = bundle

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.flFragment, recipeFormFragment)
                        .addToBackStack(null)
                        .commit()

                    Toast.makeText(requireContext(), "Editing Recipe", Toast.LENGTH_SHORT).show()
                    return@setOnMenuItemClickListener true
                }
                else -> false
            }
        }
    }

    private fun populateList(container: LinearLayout, items: List<String>, isNumbered: Boolean) {
        container.removeAllViews() // clear existing views

        items.forEachIndexed { index, item ->
            val textView = TextView(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

                setPadding(0, 4.dpToPx(), 4.dpToPx(), 4.dpToPx())

                val textToDisplay = if (isNumbered) {
                    "${index + 1}. $item"
                } else {
                    "\u2022 $item"
                }
                text = textToDisplay

                // Optional: set text appearance
                setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_BodyLarge)
            }
            container.addView(textView)
        }
    }

    private fun Int.dpToPx(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }

    override fun setupFab(fab: FloatingActionButton) {
        fab.setOnClickListener(null)
    }

    override fun showFab(): Boolean {
        return false
    }
}