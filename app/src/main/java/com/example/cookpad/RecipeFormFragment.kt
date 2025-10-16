package com.example.cookpad

import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RecipeFormFragment: Fragment(R.layout.fragment_recipe_form), FabController {
    // override functions from fabcontroller
    override fun showFab(): Boolean {
        return false
    }

    override fun setupFab(fab: FloatingActionButton) {
        fab.setOnClickListener(null)
    }
}