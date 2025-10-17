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

    // Use a mutable list and save the adapter instance
    private val shoppingList = mutableListOf<Ingredient>(
        Ingredient(name = "Flour", amount = "3 cups"),
        Ingredient(name = "Oil", amount = "3 cups"),
        Ingredient(name = "Butter", amount = "3 cups"),
        Ingredient(name = "Eggs", amount = "3 cups"),
        Ingredient(name = "Milk", amount = "3 cups", isChecked = true),
        Ingredient(name = "Oil", amount = "3 cups"),
    )
    private lateinit var shoppingListAdapter: ShoppingListAdapter

    // You'll need your Ingredient data class and ShoppingListAdapter implementation here or imported

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.shoppingListRecyclerView)

        // Initialize adapter with the mutable list
        shoppingListAdapter = ShoppingListAdapter(shoppingList)
        recyclerView.adapter = shoppingListAdapter
    }

    // --- FabLifecycleController Implementation ---
    override fun showFab(): Boolean {
        return true
    }

    override fun setupFab(fab: FloatingActionButton) {
        fab.setOnClickListener {
            val dialog = AddItemDialog()

            // Set this fragment as the target to receive the result
            dialog.setTargetFragment(this, 0)

            // Show the dialog
            dialog.show(parentFragmentManager, "AddItemDialog")
        }
    }

    // --- AddItemListener Implementation ---
    override fun onIngredientAdded(ingredient: Ingredient) {
        // This method is called by the dialog when the user clicks 'Save'

        // 1. Add the new item to the data list
        shoppingList.add(ingredient)

        // 2. Notify the adapter to refresh the RecyclerView
        shoppingListAdapter.notifyItemInserted(shoppingList.size - 1)

        // Optional: Scroll to the new item
        view?.findViewById<RecyclerView>(R.id.shoppingListRecyclerView)?.scrollToPosition(shoppingList.size - 1)

        Toast.makeText(requireContext(), "${ingredient.name} added!", Toast.LENGTH_SHORT).show()
    }
}