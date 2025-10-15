package com.example.cookpad

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class SecondFragment : Fragment(R.layout.fragment_categories) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.categoryRecyclerView)

        val categories = listOf(
            Category("Snack"),
            Category("Meat"),
            Category("Pasta"),
            Category("Chicken"),
        )

        val adapter = CategoryAdapter(categories) { category ->
            Toast.makeText(requireContext(), "Clicked on ${category.name}", Toast.LENGTH_SHORT).show()
        }

        recyclerView.adapter = adapter
    }

}