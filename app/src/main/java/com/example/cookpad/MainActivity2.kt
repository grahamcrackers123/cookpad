package com.example.cookpad

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        val fragmentContainer = findViewById<View>(R.id.flFragment)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        ViewCompat.setOnApplyWindowInsetsListener(fragmentContainer) { v, insets ->

            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        val recipesFragment = RecipesFragment()
        val categoriesFragment = CategoriesFragment()
        val shoppingListFragment = ShoppingListFragment()

        val fab: FloatingActionButton = findViewById(R.id.fabButton)

        setCurrentFragment(recipesFragment, fab)

        bottomNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.miRecipe -> setCurrentFragment(recipesFragment, fab)
                R.id.miCategories -> setCurrentFragment(categoriesFragment, fab)
                R.id.miShoppingList -> setCurrentFragment(shoppingListFragment, fab)
            }
            true
        }

        supportFragmentManager.addOnBackStackChangedListener {
            // Get the currently visible fragment
            val currentFragment = supportFragmentManager.findFragmentById(R.id.flFragment)

            if (currentFragment != null) {
                // Run the visibility logic any time the back stack changes (navigation happens)
                updateFabVisibility(currentFragment, fab)
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment, fab: FloatingActionButton) {
        // Note: The logic for FAB visibility is now handled by updateFabVisibility
        // and the BackStackListener. We call it here for the initial setup.

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            // We do NOT call addToBackStack here for the primary bottom nav fragments
            commit()
        }

        // Call the visibility helper after replacing the fragment
        updateFabVisibility(fragment, fab)
    }


    // NEW METHOD to handle the FAB logic consistently
    private fun updateFabVisibility(fragment: Fragment, fab: FloatingActionButton) {
        // MUST clear the listener first, just in case
        fab.setOnClickListener(null)
        fab.hide() // Default state is hidden

        if (fragment is FabController) {
            if (fragment.showFab()) {
                fab.show()
                fragment.setupFab(fab) // Set up the specific click listener
            }
            // If showFab() returns false, it already hit fab.hide() and fab.setOnClickListener(null)
        }
        // If the fragment doesn't implement the interface, the FAB stays hidden
    }

}