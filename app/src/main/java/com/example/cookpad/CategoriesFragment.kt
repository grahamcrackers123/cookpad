// CategoriesFragment.kt
package com.example.cookpad

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView

// Implement the AddCategoryListener interface
class CategoriesFragment : Fragment(R.layout.fragment_categories),
    FabController,
    AddCategoryDialog.AddCategoryListener {

    companion object {
        const val CATEGORY_NAME_KEY = "category_name_key"
    }

    private lateinit var categoryAdapter: CategoryAdapter

    private var displayedCategories: MutableList<Category> = mutableListOf()

    private val baseCategoryNames = listOf("Snack", "Meat", "Pasta", "Chicken", "Salad")


    // In CategoriesFragment.kt

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --- Data setup (This part is correct) ---
        val allRecipes = mockAllRecipes()
        displayedCategories.clear()
        displayedCategories.addAll(buildCategoryData(allRecipes))

        // --- View Initialization ---
        val recyclerView = view.findViewById<RecyclerView>(R.id.categoriesRecyclerView)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.categoriesToolbar)
        val searchBar = view.findViewById<SearchBar>(R.id.categoriesSearchBar)
        val searchView = view.findViewById<SearchView>(R.id.categoriesSearchView)

        // --- Adapter Setup (This is correct) ---
        categoryAdapter = CategoryAdapter(displayedCategories) { categoryName ->
            // 1. Create a Bundle to pass the clicked category's name
            val bundle = Bundle().apply {
                putString(CATEGORY_NAME_KEY, categoryName)
            }

            // 2. Create an instance of the destination fragment
            val categoryFragment = CategoryFragment()
            categoryFragment.arguments = bundle // Attach the bundle as arguments

            // 3. Perform the fragment transaction to navigate
            parentFragmentManager.beginTransaction()
                .replace(R.id.flFragment, categoryFragment) // Replace the current view with the new fragment
                .addToBackStack(null) // Add the transaction to the back stack so the user can press "back"
                .commit()
        }
        recyclerView.adapter = categoryAdapter

        // --- Search Logic to Match RecipesFragment ---

        // 1. Listen for a click on the search icon in the toolbar
        toolbar.setOnMenuItemClickListener { menuItem ->
            // Check if the clicked item is the search action
            if (menuItem.itemId == R.id.action_search) { // Ensure this ID matches your categories_menu.xml
                searchBar.visibility = View.VISIBLE // Show the search bar
                searchView.show() // Immediately open the search view
                true
            } else {
                false
            }
        }

        // 2. Connect the SearchBar and SearchView
        searchView.setupWithSearchBar(searchBar)

        // 3. Add the text listener to the SearchView for filtering
        searchView.getEditText().addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This will work correctly with your Filterable adapter
                categoryAdapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // 4. Handle the back button press inside the SearchView
        searchView.addTransitionListener { _, _, newState ->
            if (newState == SearchView.TransitionState.HIDDEN) {
                // When the search view is closed, hide the search bar again
                searchBar.visibility = View.GONE
            }
        }
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


    private fun getCategoryIcon(name: String): Int {
        when(name) {
            "Snack" -> return R.drawable.chocolate_chip_cookies
            "Meat" -> return R.drawable.chicken_pork_adobo
            "Pasta" -> return R.drawable.garlic_pasta
            "Salad" -> return R.drawable.avocado_salad
            "Chicken" -> return R.drawable.chicken_pork_adobo
            else -> return R.drawable.ic_image

        }
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