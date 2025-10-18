// CategoryFragment.kt
package com.example.cookpad

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton // For FabController

class CategoryFragment : Fragment(R.layout.fragment_recipes), FabController {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Retrieve the Category Name
        val categoryName = arguments?.getString(CategoriesFragment.CATEGORY_NAME_KEY)

        if (categoryName.isNullOrEmpty()) {
            // Handle error case: Category name not passed
            Toast.makeText(requireContext(), "Error: Category not found.", Toast.LENGTH_LONG).show()
            return
        }

        // 2. Update the Toolbar Title
        val toolbar = view.findViewById<MaterialToolbar>(R.id.topAppBar) // Assuming your XML has this ID
        toolbar?.title = categoryName

        // 3. Get all recipes (Replace this with your actual data source logic)
        val allRecipes = mockAllRecipes()

        // 4. Filter Recipes
        val filteredRecipes = allRecipes.filter { recipe ->
            recipe.categories.contains(categoryName)
        }

        // 5. Setup RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recipeRecyclerView)

        // Use StaggeredGridLayoutManager (like your main Recipes page)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        // Assuming you have a RecipeAdapter class
        val adapter = RecipeAdapter(filteredRecipes) { recipe ->
            // Click listener logic: Navigate to the RecipeDetailFragment
            // (Similar to what you have in RecipesFragment.kt)
            val bundle = Bundle().apply {
                putParcelable(RecipesFragment.RECIPE_KEY, recipe)
            }
            val recipeDetailFragment = RecipeFragment()
            recipeDetailFragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.flFragment, recipeDetailFragment)
                .addToBackStack(null)
                .commit()
        }

        recyclerView.adapter = adapter
    }

    // placeholder
    private fun mockAllRecipes(): List<Recipe> {
        return listOf(
            Recipe("Truffle Pasta", R.drawable.avocado_salad, emptyList(), emptyList(), listOf("Dinner", "Pasta")),
            Recipe("Adobo", R.drawable.avocado_salad, emptyList(), emptyList(), listOf("Dinner", "Meat")),
            Recipe("Breakfast Casserole", R.drawable.avocado_salad, emptyList(), emptyList(), listOf("Breakfast")),
            Recipe("Veggie Stir Fry", R.drawable.avocado_salad, emptyList(), emptyList(), listOf("Vegetarian")),
            Recipe("Chicken Adobo", R.drawable.avocado_salad, emptyList(), emptyList(), listOf("Chicken", "Dinner")),
        )
    }

    override fun showFab(): Boolean {
        return false
    }

    override fun setupFab(fab: FloatingActionButton) {
        fab.setOnClickListener(null)
    }
}