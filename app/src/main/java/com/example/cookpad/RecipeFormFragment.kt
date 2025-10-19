package com.example.cookpad

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RecipeFormFragment: Fragment(R.layout.fragment_recipe_form), FabController {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<MaterialToolbar>(R.id.recipeFormTopAppBar)

        toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun showFab(): Boolean {
        return false
    }

    override fun setupFab(fab: FloatingActionButton) {
        fab.setOnClickListener(null)
    }
}