package com.example.cookpad

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton


class RecipesFragment : Fragment(R.layout.fragment_recipes), FabController {

    companion object {
        const val RECIPE_KEY = "recipe_detail_key"
        const val FORM_MODE_KEY = "form_mode"
        const val MODE_NEW_RECIPE = "new"
        const val MODE_EDIT_RECIPE = "edit"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchBar = view.findViewById<SearchBar>(R.id.searchbarRecipe)
        val searchView = view.findViewById<SearchView>(R.id.searchviewRecipe)

        searchBar.setOnMenuItemClickListener {
            true
        }
        searchView.setOnClickListener {
            searchView.show()
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.recipeRecyclerView)

        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        val recipeList = listOf(
            Recipe(
                title = "Truffle Pasta",
                image = R.drawable.avocado_salad,
                ingredients = listOf(
                    "2 tbsp butter",
                    "2 tbsp all purpose flour",
                    "1&½ cups milk",
                    "½ tsp black pepper",
                    "1 cup freshly grated parmigian cheese",
                    "2 tbsp truffle oil",
                    "¼ cup onion or shallots finely diced",
                    "8oz half a box of thin spaghetti cooked, drained"
                ),
                steps = listOf(
                    "In a pot, melt butter and add  flour. On med-low heat mix the roux until its a slight golden brown and  add the onions. Cook for 2 mins until onions are slightly cooked and  wilted down and add the truffle oil.",
                    "Next add the milk and whisk  out any clumps from the roux until you have a smooth consistency. Lower  the heat and bring to a simmer.",
                    "Add the parmigiana cheese,  black pepper and salt to taste. Keep stirring until the sauce starts to  thicken. Add in the cooked pasta and serve while its hot."
                )
            ),
            Recipe(
                title = "Adobo",
                image = R.drawable.avocado_salad,
                ingredients = listOf(""),
                steps = listOf("")
            ),
            Recipe(
                title = "Carbonara",
                image = R.drawable.avocado_salad,
                ingredients = listOf(""),
                steps = listOf("")
            ),
            Recipe(
                title = "Caldereta",
                image = R.drawable.avocado_salad,
                ingredients = listOf(""),
                steps = listOf("")
            ),
            Recipe(
                title = "Pancake",
                image = R.drawable.avocado_salad,
                ingredients = listOf(""),
                steps = listOf("")
            ),
            Recipe(
                title = "Adobo",
                image = R.drawable.avocado_salad,
                ingredients = listOf(""),
                steps = listOf("")
            ),
            Recipe(
                title = "Carbonara",
                image = R.drawable.avocado_salad,
                ingredients = listOf(""),
                steps = listOf("")
            ),
            Recipe(
                title = "Caldereta",
                image = R.drawable.avocado_salad,
                ingredients = listOf(""),
                steps = listOf("")
            ),
            Recipe(
                title = "Pancake",
                image = R.drawable.avocado_salad,
                ingredients = listOf(""),
                steps = listOf("")
            ),
            Recipe(
                title = "Adobo",
                image = R.drawable.avocado_salad,
                ingredients = listOf(""),
                steps = listOf("")
            ),
            Recipe(
                title = "Carbonara",
                image = R.drawable.avocado_salad,
                ingredients = listOf(""),
                steps = listOf("")
            ),
            Recipe(
                title = "Caldereta",
                image = R.drawable.avocado_salad,
                ingredients = listOf(""),
                steps = listOf("")
            ),
            Recipe(
                title = "Pancake",
                image = R.drawable.avocado_salad,
                ingredients = listOf(""),
                steps = listOf("")
            )
        )

        val adapter = RecipeAdapter(recipeList) { recipe ->

            // bundle with parcelable recipe
            val bundle = Bundle().apply {
                putParcelable(RECIPE_KEY, recipe)
            }

            // destination fragment
            val recipeDetailFragment = RecipeFragment()
            recipeDetailFragment.arguments = bundle // Attach the data

            // fragment transaction
            parentFragmentManager.beginTransaction()
                .replace(R.id.flFragment, recipeDetailFragment)
                .addToBackStack(null)
                .commit()
            // -----------------------------
        }

        recyclerView.adapter = adapter

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