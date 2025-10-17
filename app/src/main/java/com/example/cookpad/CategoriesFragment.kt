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

    private lateinit var categoryAdapter: CategoryAdapter
    private val categories = mutableListOf<Category>(
        Category("Snack"),
        Category("Meat"),
        Category("Pasta"),
        Category("Chicken"),
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.categoryRecyclerView)

        // Initialize adapter with mutable list
        categoryAdapter = CategoryAdapter(categories) { category ->
            Toast.makeText(requireContext(), "Clicked on ${category.name}", Toast.LENGTH_SHORT).show()
        }

        recyclerView.adapter = categoryAdapter
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
        // This is called when the "Add" button in the dialog is clicked

        val newCategory = Category(categoryName)

        // 1. Add the new category to the list
        categories.add(newCategory)

        // 2. Notify the adapter to update the RecyclerView
        categoryAdapter.notifyItemInserted(categories.size - 1)

        Toast.makeText(requireContext(), "$categoryName added!", Toast.LENGTH_SHORT).show()
    }
}