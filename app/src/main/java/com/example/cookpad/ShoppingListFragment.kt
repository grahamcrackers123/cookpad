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
            Ingredient(name = "Flour", amount = "3 cups"),
            Ingredient(name = "Oil", amount = "3 cups"),
            Ingredient(name = "Butter", amount = "3 cups"),
            Ingredient(name = "Eggs", amount = "3 cups"),
            Ingredient(name = "Milk", amount = "3 cups", isChecked = true),
            Ingredient(name = "Oil", amount = "3 cups"),
        )

        val adapter = ShoppingListAdapter(shoppingList)
        recyclerView.adapter = adapter
    }
}