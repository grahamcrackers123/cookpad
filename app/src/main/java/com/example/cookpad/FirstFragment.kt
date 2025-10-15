package com.example.cookpad

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager


class FirstFragment : Fragment(R.layout.fragment_recipes) {

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
            Recipe("Spaghetti", R.drawable.avocado_salad),
            Recipe("Adobo", R.drawable.avocado_salad),
            Recipe("Carbonara", R.drawable.avocado_salad),
            Recipe("Caldereta", R.drawable.avocado_salad),
            Recipe("Pancake", R.drawable.avocado_salad),
            Recipe("Spaghetti", R.drawable.avocado_salad),
            Recipe("Adobo", R.drawable.avocado_salad),
            Recipe("Carbonara", R.drawable.avocado_salad),
            Recipe("Caldereta", R.drawable.avocado_salad),
            Recipe("Pancake", R.drawable.avocado_salad),
            Recipe("Spaghetti", R.drawable.avocado_salad),
            Recipe("Adobo", R.drawable.avocado_salad),
            Recipe("Carbonara", R.drawable.avocado_salad),
            Recipe("Caldereta", R.drawable.avocado_salad),
            Recipe("Pancake", R.drawable.avocado_salad),
        )

        val adapter = RecipeAdapter(recipeList) { recipe ->
            Toast.makeText(requireContext(), "Clicked on ${recipe.name}", Toast.LENGTH_SHORT).show()
        }

        recyclerView.adapter = adapter

    }

}