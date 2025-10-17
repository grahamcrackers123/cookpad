package com.example.cookpad

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import android.widget.Button
import android.widget.Toast

class AddItemDialog : DialogFragment() {

    interface AddItemListener {
        fun onIngredientAdded(ingredient: Ingredient)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.popup_add_list_item, null, false)

        // --- View Bindings (Requires IDs in your XML) ---
        val nameEditText = dialogView.findViewById<TextInputEditText>(R.id.ingredientNameEditText)
        val amountEditText = dialogView.findViewById<TextInputEditText>(R.id.ingredientAmountEditText)
        val saveButton = dialogView.findViewById<Button>(R.id.saveIngredientButton)
        val cancelButton = dialogView.findViewById<Button>(R.id.cancelIngredientButton)

        // --- Setup Click Listeners ---

        saveButton.setOnClickListener {
            val name = nameEditText.text?.toString()?.trim()
            val amount = amountEditText.text?.toString()?.trim()

            if (!name.isNullOrEmpty() && !amount.isNullOrEmpty()) {
                val newIngredient = Ingredient(
                    name = name,
                    amount = amount
                )
                // Send the new item back to the listener
                (targetFragment as? AddItemListener)?.onIngredientAdded(newIngredient)
                dismiss() // Close the dialog
            } else {
                Toast.makeText(context, "Please enter both name and amount.", Toast.LENGTH_SHORT).show()
            }
        }

        cancelButton.setOnClickListener {
            dismiss() // Close the dialog
        }

        // --- Build the AlertDialog ---
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)

        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        return dialog
    }
}