package com.example.cookpad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AddIngredientsDialog : DialogFragment(), IngredientTransferListener {

    private lateinit var initialRecipeIngredients: List<Ingredient>

    private lateinit var availableAdapter: IngredientTransferAdapter
    private lateinit var addedAdapter: IngredientTransferAdapter

    fun setAvailableIngredients(ingredients: List<Ingredient>) {
        // Store the incoming data for use in onViewCreated
        initialRecipeIngredients = ingredients
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.popup_add_to_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val availableList: MutableList<Ingredient> = initialRecipeIngredients.toMutableList()
        val addedList: MutableList<Ingredient> = mutableListOf()

        val availableRecycler: RecyclerView = view.findViewById(R.id.availableIngredientsRecyclerView)
        val addedRecycler: RecyclerView = view.findViewById(R.id.addedIngredientsRecyclerView)

        // adapters
        // Give each adapter own copy of list
        availableAdapter = IngredientTransferAdapter(ArrayList(availableList), true, this)
        addedAdapter = IngredientTransferAdapter(ArrayList(addedList), false, this)

        // link adapters to recyclerviews
        availableRecycler.layoutManager = LinearLayoutManager(context)
        availableRecycler.adapter = availableAdapter

        addedRecycler.layoutManager = LinearLayoutManager(context)
        addedRecycler.adapter = addedAdapter

        // Setup Save/Cancel button listeners here
    }

    override fun onIngredientAdded(ingredient: Ingredient) {
        // from available to added
        availableAdapter.removeItem(ingredient)
        addedAdapter.addItem(ingredient)
    }

    override fun onIngredientRemoved(ingredient: Ingredient) {
        // from added to available
        addedAdapter.removeItem(ingredient)
        availableAdapter.addItem(ingredient)
    }

    private fun getInitialAvailableIngredients() = listOf(
        Ingredient(name = "Water", amount = "1/2 cup"),
        Ingredient(name = "Flour", amount = "2 cups"),
        Ingredient(name = "Sugar", amount = "1 cup"),
        Ingredient(name = "Salt", amount = "1 tsp"),
        Ingredient(name = "Eggs", amount = "3 pcs")
    )
}