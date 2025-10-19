package com.example.cookpad

import ShoppingListAdapter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.Toast

class ShoppingListFragment : Fragment(R.layout.fragment_shopping_list),
    FabController,
    AddItemDialog.AddItemListener {

    private val shoppingList = mutableListOf(
        Ingredient(name = "Flour", amount = "3 cups"),
        Ingredient(name = "Oil", amount = "3 cups"),
        Ingredient(name = "Butter", amount = "3 cups"),
        Ingredient(name = "Eggs", amount = "3 cups"),
        Ingredient(name = "Milk", amount = "3 cups", isChecked = true),
        Ingredient(name = "Oil", amount = "3 cups"),
    )
    private lateinit var shoppingListAdapter: ShoppingListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.shoppingListRecyclerView)

        shoppingListAdapter = ShoppingListAdapter(shoppingList)
        recyclerView.adapter = shoppingListAdapter
    }

    override fun showFab(): Boolean {
        return true
    }

    override fun setupFab(fab: FloatingActionButton) {
        fab.setOnClickListener {
            val dialog = AddItemDialog()

            dialog.setTargetFragment(this, 0)

            dialog.show(parentFragmentManager, "AddItemDialog")
        }
    }

    override fun onIngredientAdded(ingredient: Ingredient) {

        shoppingList.add(ingredient)

        shoppingListAdapter.notifyItemInserted(shoppingList.size - 1)

        view?.findViewById<RecyclerView>(R.id.shoppingListRecyclerView)?.scrollToPosition(shoppingList.size - 1)

        Toast.makeText(requireContext(), "${ingredient.name} added!", Toast.LENGTH_SHORT).show()
    }
}