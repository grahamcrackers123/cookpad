package com.example.cookpad

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView

class CategoriesFragment : Fragment(R.layout.fragment_categories),
    FabController,
    AddCategoryDialog.AddCategoryListener {

    companion object {
        const val CATEGORY_NAME_KEY = "category_name_key"
    }

    private lateinit var categoryAdapter: CategoryAdapter

    private var displayedCategories: MutableList<Category> = mutableListOf()

    private val baseCategoryNames = listOf("Snack", "Meat", "Pasta", "Chicken", "Salad")



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val allRecipes = mockAllRecipes()
        displayedCategories.clear()
        displayedCategories.addAll(buildCategoryData(allRecipes))

        val recyclerView = view.findViewById<RecyclerView>(R.id.categoriesRecyclerView)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.categoriesToolbar)
        val searchBar = view.findViewById<SearchBar>(R.id.categoriesSearchBar)
        val searchView = view.findViewById<SearchView>(R.id.categoriesSearchView)

        categoryAdapter = CategoryAdapter(displayedCategories) { categoryName ->
            val bundle = Bundle().apply {
                putString(CATEGORY_NAME_KEY, categoryName)
            }

            val categoryFragment = CategoryFragment()
            categoryFragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.flFragment, categoryFragment)
                .addToBackStack(null)
                .commit()
        }
        recyclerView.adapter = categoryAdapter


        toolbar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.action_search) {
                searchBar.visibility = View.VISIBLE
                searchView.show()
                true
            } else {
                false
            }
        }

        searchView.setupWithSearchBar(searchBar)

        searchView.getEditText().addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                categoryAdapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        searchView.addTransitionListener { _, _, newState ->
            if (newState == SearchView.TransitionState.HIDDEN) {
                searchBar.visibility = View.GONE
            }
        }

        val loadingProgressBar = view.findViewById<ProgressBar>(R.id.loadingCategoriesProgressBar)
        val emptyStateTextView = view.findViewById<TextView>(R.id.emptyCategoriesStateTextView)

        loadingProgressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        emptyStateTextView.visibility = View.GONE

        loadingProgressBar.visibility = View.GONE
        if (displayedCategories.isNotEmpty()) {
            recyclerView.visibility = View.VISIBLE
        } else {
            emptyStateTextView.text = getString(R.string.no_categories_found)
            emptyStateTextView.visibility = View.VISIBLE
        }

    }



    private fun buildCategoryData(allRecipes: List<Recipe>): List<Category> {
        val categoryMap = mutableMapOf<String, MutableList<String>>()

        allRecipes.forEach { recipe ->
            recipe.categories.forEach { categoryName ->
                categoryMap.getOrPut(categoryName) { mutableListOf() }.add(recipe.title)
            }
        }

        baseCategoryNames.forEach { name ->
            categoryMap.putIfAbsent(name, mutableListOf())
        }

        return categoryMap.map { (name, titles) ->
            Category(
                name = name,
                image = getCategoryIcon(name),
                previewRecipeNames = titles.take(3)
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

    private fun mockAllRecipes(): List<Recipe> {
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
            val dialog = AddCategoryDialog()

            dialog.setTargetFragment(this, 0)

            dialog.show(parentFragmentManager, "AddCategoryDialog")
        }
    }

    override fun onCategoryAdded(categoryName: String) {
        if (displayedCategories.any { it.name.equals(categoryName, ignoreCase = true) }) {
            Toast.makeText(requireContext(), "$categoryName already exists!", Toast.LENGTH_SHORT).show()
            return
        }

        val newCategory = Category(
            name = categoryName,
            image = getCategoryIcon(categoryName),
            previewRecipeNames = emptyList()
        )

        displayedCategories.add(newCategory)

        categoryAdapter.notifyItemInserted(displayedCategories.size - 1)

        Toast.makeText(requireContext(), "$categoryName added!", Toast.LENGTH_SHORT).show()
    }
}