package com.example.cookpad

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView

class RecipesFragment : Fragment(R.layout.fragment_recipes), FabController {

    companion object {
        const val RECIPE_KEY = "recipe_detail_key"
        const val FORM_MODE_KEY = "form_mode"
        const val MODE_NEW_RECIPE = "new"
        const val MODE_EDIT_RECIPE = "edit"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<MaterialToolbar>(R.id.recipesToolbar)
        val searchBar = view.findViewById<SearchBar>(R.id.recipesSearchBar)
        val searchView = view.findViewById<SearchView>(R.id.recipesSearchView)

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

        val recyclerView: RecyclerView = view.findViewById(R.id.recipeRecyclerView)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        // Your existing recipe list and adapter setup
        val recipeList = listOf(
            Recipe(
                title = "Truffle Pasta",
                image = R.drawable.avocado_salad,
                ingredients = listOf(
                    "butter",
                    "all purpose flour",
                    "cups milk",
                    "black pepper",
                    "freshly grated parmigian cheese",
                    "truffle oil",
                    "onion or shallots finely diced",
                    "half a box of thin spaghetti cooked, drained"
                ),
                measurements = listOf(
                    "2 tbsp",
                    "2 tbsp",
                    "1&½ cups",
                    "½ tsp",
                    "1 cup",
                    "2 tbsp",
                    "¼ cup",
                    "8oz"
                ),
                steps = listOf(
                    "In a pot, melt butter and add  flour. On med-low heat mix the roux until its a slight golden brown and  add the onions. Cook for 2 mins until onions are slightly cooked and  wilted down and add the truffle oil.",
                    "Next add the milk and whisk  out any clumps from the roux until you have a smooth consistency. Lower  the heat and bring to a simmer.",
                    "Add the parmigiana cheese,  black pepper and salt to taste. Keep stirring until the sauce starts to  thicken. Add in the cooked pasta and serve while its hot."
                ),
                categories = listOf("Dinner", "Pasta")
            ),
            // ... other recipes
        )

        val adapter = RecipeAdapter(recipeList) { recipe ->
            val bundle = Bundle().apply {
                putParcelable(RECIPE_KEY, recipe)
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

    // Your existing FabController methods remain unchanged
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
