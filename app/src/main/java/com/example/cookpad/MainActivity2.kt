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
                0
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
            val currentFragment = supportFragmentManager.findFragmentById(R.id.flFragment)

            if (currentFragment != null) {
                updateFabVisibility(currentFragment, fab)
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment, fab: FloatingActionButton) {

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }

        updateFabVisibility(fragment, fab)
    }

    private fun updateFabVisibility(fragment: Fragment, fab: FloatingActionButton) {
        fab.setOnClickListener(null)
        fab.hide()

        if (fragment is FabController) {
            if (fragment.showFab()) {
                fab.show()
                fragment.setupFab(fab)
            }
        }
    }

}