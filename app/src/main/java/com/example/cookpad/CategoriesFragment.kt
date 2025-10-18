// CategoriesFragment.kt
package com.example.cookpad

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

// Implement the AddCategoryListener interface
class CategoriesFragment : Fragment(R.layout.fragment_categories),
    FabController,
    AddCategoryDialog.AddCategoryListener {

    companion object {
        const val CATEGORY_NAME_KEY = "category_name_key"
    }

    private lateinit var categoryAdapter: CategoryAdapter

    private var displayedCategories: MutableList<Category> = mutableListOf()

    private val baseCategoryNames = listOf("Snack", "Meat", "Pasta", "Chicken", "Vegetarian", "Dessert")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --- IMPORTANT: Simulate getting all recipes to create previews ---
        val allRecipes = mockAllRecipes() // You need a function to get ALL recipes

        displayedCategories.clear()
        displayedCategories.addAll(buildCategoryData(allRecipes))

        val recyclerView = view.findViewById<RecyclerView>(R.id.categoryRecyclerView)

        // Initialize adapter with mutable list
        categoryAdapter = CategoryAdapter(displayedCategories) { categoryName ->
            // --- NEW NAVIGATION: Go to filtered recipe list ---
            val bundle = Bundle().apply {
                putString(CATEGORY_NAME_KEY, categoryName)
            }

            val categoryFragment = CategoryFragment() // New fragment
            categoryFragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.flFragment, categoryFragment)
                .addToBackStack(null)
                .commit()
        }
        recyclerView.adapter = categoryAdapter
    }

    // --- Helper function to restructure data for the category list ---
    private fun buildCategoryData(allRecipes: List<Recipe>): List<Category> {
        val categoryMap = mutableMapOf<String, MutableList<String>>()

        // 1. Group recipes by category
        allRecipes.forEach { recipe ->
            recipe.categories.forEach { categoryName ->
                categoryMap.getOrPut(categoryName) { mutableListOf() }.add(recipe.title)
            }
        }

        baseCategoryNames.forEach { name ->
            categoryMap.putIfAbsent(name, mutableListOf())
        }

        // 2. Create Category objects
        return categoryMap.map { (name, titles) ->
            Category(
                name = name,
                image = getCategoryIcon(name), // Implement this function to get a default icon
                previewRecipeNames = titles.take(3) // Take the first 3 names for the preview
            )
        }
    }

    // Placeholder for getting a category image
    private fun getCategoryIcon(name: String): Int {
        return R.drawable.ic_image
    }

    // Placeholder for all recipes data
    private fun mockAllRecipes(): List<Recipe> {
        // This list must come from your actual data source (DB/API/mock data)
        return listOf(
            Recipe("Truffle Pasta", R.drawable.avocado_salad, emptyList(), emptyList(), listOf("Dinner", "Pasta")),
            Recipe("Adobo", R.drawable.avocado_salad, emptyList(), emptyList(), listOf("Dinner", "Meat")),
            Recipe("Carbonara", R.drawable.avocado_salad, emptyList(), emptyList(), listOf("Pasta")),
        )
    }

    override fun showFab(): Boolean {
        return true
    }

    override fun setupFab(fab: FloatingActionButton) {
        fab.setOnClickListener {
            // 1. Create the dialog instance
            val dialog = AddCategoryDialog()

            // 2. Set this fragment as the target (to receive the result)
            dialog.setTargetFragment(this, 0)

            // 3. Show the dialog
            dialog.show(parentFragmentManager, "AddCategoryDialog")
        }
    }

    // --- Implementation of the AddCategoryListener interface ---
    override fun onCategoryAdded(categoryName: String) {
        // **Issue Fix 1: Check for existing category before adding**
        if (displayedCategories.any { it.name.equals(categoryName, ignoreCase = true) }) {
            Toast.makeText(requireContext(), "$categoryName already exists!", Toast.LENGTH_SHORT).show()
            return
        }

        // **Issue Fix 2: Use the full Category data structure**
        val newCategory = Category(
            name = categoryName,
            image = getCategoryIcon(categoryName), // Use your existing placeholder function
            previewRecipeNames = emptyList() // New categories have no recipes yet
        )

        // 1. Add the new category to the **class-level list**
        displayedCategories.add(newCategory)

        // 2. Notify the adapter to update the RecyclerView
        categoryAdapter.notifyItemInserted(displayedCategories.size - 1)

        Toast.makeText(requireContext(), "$categoryName added!", Toast.LENGTH_SHORT).show()
    }
}