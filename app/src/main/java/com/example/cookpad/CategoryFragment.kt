package com.example.cookpad

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.search.SearchView

// This fragment uses the 'fragment_recipes.xml' layout to look identical.
class CategoryFragment : Fragment(R.layout.fragment_recipes), FabController {

    private lateinit var recipeAdapter: RecipeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Retrieve the category name passed from CategoriesFragment
        val categoryName = arguments?.getString(CategoriesFragment.CATEGORY_NAME_KEY)

        if (categoryName.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Error: Category not found.", Toast.LENGTH_LONG).show()
            parentFragmentManager.popBackStack() // Go back if there's an error
            return
        }

        // 2. Find the toolbar from fragment_recipes.xml and set the title
        val toolbar = view.findViewById<MaterialToolbar>(R.id.recipesToolbar)
        val searchView = view.findViewById<SearchView>(R.id.recipesSearchView)

        toolbar.title = categoryName

        toolbar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.action_search) {
                toolbar.visibility = View.GONE
                searchView.show()
                true
            } else {
                false
            }
        }
        searchView.addTransitionListener { _, _, newState ->
            if (newState == SearchView.TransitionState.HIDDEN || newState == SearchView.TransitionState.HIDING) {
                toolbar.visibility = View.VISIBLE
            }
        }



        // 3. Get all recipes from your mock data source
        val allRecipes = mockAllRecipes()

        // 4. Filter the recipes to get only the ones in the current category
        val filteredRecipes = allRecipes.filter { recipe ->
            // Check if the recipe's categories list contains the categoryName
            recipe.categories.any { it.equals(categoryName, ignoreCase = true) }
        }

        // 5. Setup RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recipeRecyclerView)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        // 6. Initialize the RecipeAdapter with the filtered list
        recipeAdapter = RecipeAdapter(filteredRecipes.toMutableList()) { recipe ->
            // Navigation logic to go to the recipe detail screen
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
        recyclerView.adapter = recipeAdapter

        // If the list is empty, you could show a message
        if (filteredRecipes.isEmpty()) {
            Toast.makeText(requireContext(), "No recipes found in $categoryName.", Toast.LENGTH_SHORT).show()
        }
    }

    // This must be the same source of data as used elsewhere
    private fun mockAllRecipes(): List<Recipe> {
        return listOf(
            Recipe("Garlic Pasta", R.drawable.garlic_pasta, emptyList(), emptyList(), emptyList(), listOf("Pasta")),
            Recipe("Adobo", R.drawable.chicken_pork_adobo, emptyList(), emptyList(), emptyList(),  listOf("Dinner", "Chicken")),
            Recipe("Cookies", R.drawable.chocolate_chip_cookies, emptyList(), emptyList(), emptyList(),  listOf("Snack")),
            Recipe("Caldereta", R.drawable.caldereta, emptyList(), emptyList(), emptyList(),  listOf("Dinner")),
        )
    }

    // Hide the Floating Action Button on this screen
    override fun showFab(): Boolean {
        return false
    }

    override fun setupFab(fab: FloatingActionButton) {
        fab.setOnClickListener(null)
    }
}
