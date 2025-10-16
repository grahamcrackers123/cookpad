package com.example.cookpad
import com.google.android.material.floatingactionbutton.FloatingActionButton

interface FabController {
    fun setupFab(fab: FloatingActionButton)
    fun showFab(): Boolean
}