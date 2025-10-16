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
            val ingredientsContainer: LinearLayout = view.findViewById(R.id.ingredientsContainer)
            val stepsContainer: LinearLayout = view.findViewById(R.id.stepsContainer)

            titleTextView.text = recipeDetail.title
            toolbar.title = recipeDetail.title
            Glide.with(this).load(recipeDetail.image).into(imageView)

            populateList(ingredientsContainer, recipeDetail.ingredients, isNumbered = false)
            populateList(stepsContainer, recipeDetail.steps, isNumbered = true)

            setupToolbar(toolbar)

        } else {
            Toast.makeText(requireContext(), "Error: Recipe data missing.", Toast.LENGTH_LONG).show()
        }

    }

    private fun setupToolbar(toolbar: Toolbar) {
        // back button
        toolbar.setNavigationOnClickListener {
            Toast.makeText(requireContext(), "Going back...", Toast.LENGTH_SHORT).show()
        }

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.addToListAction -> {
                    Toast.makeText(requireContext(), "Added to Shopping List", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.editAction -> {
                    // --- REDIRECT TO FORM WITH RECIPE DATA ---

                    // 1. Get the current Recipe object (it was passed to this fragment)
                    val currentRecipe = arguments?.getParcelable<Recipe>(RecipesFragment.RECIPE_KEY)

                    if (currentRecipe != null) {
                        // 2. Create the Bundle and pass the existing Recipe
                        val bundle = Bundle().apply {
                            // Pass the entire Recipe object for editing
                            putParcelable(RecipesFragment.RECIPE_KEY, currentRecipe)

                            // Optional: Pass an explicit mode indicator
                            putString(RecipesFragment.FORM_MODE_KEY, RecipesFragment.MODE_EDIT_RECIPE)
                        }

                        // 3. Perform the Fragment Transaction
                        val recipeFormFragment = RecipeFormFragment()
                        recipeFormFragment.arguments = bundle

                        parentFragmentManager.beginTransaction()
                            .replace(R.id.flFragment, recipeFormFragment)
                            .addToBackStack(null)
                            .commit()
                    } else {
                        Toast.makeText(requireContext(), "Error: Cannot find recipe data to edit.", Toast.LENGTH_SHORT).show()
                    }
                    true
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