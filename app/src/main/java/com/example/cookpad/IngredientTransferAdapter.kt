package com.example.cookpad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

// 1. Define the callback interface
interface IngredientTransferListener {
    // Called when an item is added to the list (transfer from Available to Added)
    fun onIngredientAdded(ingredient: Ingredient)
    // Called when an item is removed from the list (transfer from Added to Available)
    fun onIngredientRemoved(ingredient: Ingredient)
}

class IngredientTransferAdapter(
    private val items: MutableList<Ingredient>,
    private val isAvailableList: Boolean, // True for the top list, False for the bottom list
    private val listener: IngredientTransferListener
) : RecyclerView.Adapter<IngredientTransferAdapter.IngredientViewHolder>() {

    inner class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(
            if (isAvailableList) R.id.availableIngredientTextView else R.id.addedIngredientTextView
        )
        private val quantityTextView: TextView = itemView.findViewById(
            if (isAvailableList) R.id.availableMeasurementTextView else R.id.addedMeasurementTextView
        )
        private val actionButton: MaterialButton = itemView.findViewById(
            if (isAvailableList) R.id.addButton else R.id.removeButton
        )

        fun bind(ingredient: Ingredient) {
            nameTextView.text = ingredient.name
            quantityTextView.text = ingredient.amount

            // Set the action based on which list this adapter belongs to
            actionButton.setOnClickListener {
                if (isAvailableList) {
                    listener.onIngredientAdded(ingredient)
                } else {
                    listener.onIngredientRemoved(ingredient)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val layoutId = if (isAvailableList) R.layout.list_item_available else R.layout.list_item_added
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    // Crucial function for updating the adapter after a transfer
    fun removeItem(ingredient: Ingredient) {
        val index = items.indexOfFirst { it.id == ingredient.id }
        if (index != -1) {
            items.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun addItem(ingredient: Ingredient) {
        // Simple add, but you might want to sort this in a real app
        items.add(ingredient)
        notifyItemInserted(items.size - 1)
    }
}