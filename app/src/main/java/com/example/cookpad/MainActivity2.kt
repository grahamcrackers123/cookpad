package com.example.cookpad

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

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
        val secondFragment = SecondFragment()
        val shoppingListFragment = ShoppingListFragment()

        setCurrentFragment(recipesFragment)

        bottomNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.miRecipe -> setCurrentFragment(recipesFragment)
                R.id.miCategories -> setCurrentFragment(secondFragment)
                R.id.miShoppingList -> setCurrentFragment(shoppingListFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }


}