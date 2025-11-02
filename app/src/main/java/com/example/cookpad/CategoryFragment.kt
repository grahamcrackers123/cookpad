package com.example.cookpad

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cookpad.RecipesFragment.Companion.FORM_MODE_KEY
import com.example.cookpad.RecipesFragment.Companion.MODE_NEW_RECIPE
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.search.SearchView

class CategoryFragment : Fragment(R.layout.fragment_recipes), FabController {

    private lateinit var recipeAdapter: RecipeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loadingProgressBar = view.findViewById<View>(R.id.loadingRecipesProgressBar) // Or whatever the ID is
        val recyclerView: RecyclerView = view.findViewById(R.id.recipeRecyclerView)
        val emptyStateTextView = view.findViewById<TextView>(R.id.emptyRecipesStateTextView)


        val categoryName = arguments?.getString(CategoriesFragment.CATEGORY_NAME_KEY)

        if (categoryName.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Error: Category not found.", Toast.LENGTH_LONG).show()
            parentFragmentManager.popBackStack()
            return
        }

        val toolbar = view.findViewById<MaterialToolbar>(R.id.recipesToolbar)
        val searchView = view.findViewById<SearchView>(R.id.recipesSearchView)

        toolbar.title = categoryName
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
        val navIconTintColor = resources.getColor(R.color.toolbar_icon_color, null)
        toolbar.navigationIcon?.setTint(navIconTintColor)

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

        loadingProgressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        emptyStateTextView.visibility = View.GONE

        val allRecipes = mockAllRecipes()

        val filteredRecipes = allRecipes.filter { recipe ->
            recipe.categories.any { it.equals(categoryName, ignoreCase = true) }
        }


        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        recipeAdapter = RecipeAdapter(filteredRecipes.toMutableList()) { recipe ->
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

        loadingProgressBar.visibility = View.GONE

        if (filteredRecipes.isEmpty()) {
            emptyStateTextView.text = "No recipes found in '$categoryName'"
            emptyStateTextView.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            // Show the recycler view if recipes were found
            emptyStateTextView.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    private fun mockAllRecipes(): List<Recipe> {
        return listOf(
            Recipe("Garlic Pasta", R.drawable.garlic_pasta, emptyList(), emptyList(), emptyList(), listOf("Pasta")),
            Recipe("Adobo", R.drawable.chicken_pork_adobo, emptyList(), emptyList(), emptyList(),  listOf("Dinner", "Chicken")),
            Recipe("Cookies", R.drawable.chocolate_chip_cookies, emptyList(), emptyList(), emptyList(),  listOf("Snack")),
            Recipe("Caldereta", R.drawable.caldereta, emptyList(), emptyList(), emptyList(),  listOf("Dinner")),
        )
    }

    override fun showFab(): Boolean {
        return true
    }

    override fun setupFab(fab: FloatingActionButton) {
        fab.setOnClickListener {
            val bundle = Bundle().apply {
                putString(FORM_MODE_KEY, MODE_NEW_RECIPE)
            }
            val recipeFormFragment = RecipeFormFragment()
            recipeFormFragment.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.flFragment, recipeFormFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
