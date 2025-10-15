package com.example.cookpad

import ShoppingListAdapter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class ShoppingListFragment : Fragment(R.layout.fragment_shopping_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.shoppingListRecyclerView)

        val shoppingList = listOf(
            IngredientItem("Flour", "3 cups"),
            IngredientItem("Butter", "3 cups"),
            IngredientItem("Eggs", "3 cups"),
            IngredientItem("Milk", "3 cups"),
            IngredientItem("Oil", "3 cups", true),
            IngredientItem("Flour", "3 cups")
        )

        val adapter = ShoppingListAdapter(shoppingList)
        recyclerView.adapter = adapter
    }
}