package com.example.cookpad

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide // For image loading

class RecipeDetailFragment : Fragment(R.layout.fragment_recipe) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Retrieve the Recipe object from arguments
        val recipeDetail = arguments?.getParcelable<Recipe>(RecipesFragment.RECIPE_KEY)

        // Check if data was successfully passed
        if (recipeDetail != null) {

            // --- Existing View Bindings (use RecipeDetail's properties) ---
            val toolbar: Toolbar = view.findViewById(R.id.toolbarRecipeDetail)
            val imageView: ImageView = view.findViewById(R.id.recipeDetailImage)
            val titleTextView: TextView = view.findViewById(R.id.recipeDetailTitle)
            val ingredientsContainer: LinearLayout = view.findViewById(R.id.ingredientsContainer)
            val stepsContainer: LinearLayout = view.findViewById(R.id.stepsContainer)

            // 2. Populate views with the received data
            titleTextView.text = recipeDetail.title
            toolbar.title = recipeDetail.title // Set the toolbar title
            Glide.with(this).load(recipeDetail.image).into(imageView)

            // Populate the lists
            populateList(ingredientsContainer, recipeDetail.ingredients, isNumbered = false)
            populateList(stepsContainer, recipeDetail.steps, isNumbered = true)

            // 3. Setup Toolbar Actions (using the received data)
            setupToolbar(toolbar)

        } else {
            // Handle case where no recipe data was passed (e.g., show error, pop back)
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
                    Toast.makeText(requireContext(), "Editing Recipe", Toast.LENGTH_SHORT).show()
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
}