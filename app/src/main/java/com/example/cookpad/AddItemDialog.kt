package com.example.cookpad

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

class AddItemDialog : DialogFragment() {

    override fun onStart() {
        super.onStart()

        dialog?.window?.let { window ->
            // dialog width is a percentage of screen
            val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
            window.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    interface AddItemListener {
        fun onIngredientAdded(ingredient: Ingredient)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.popup_add_list_item, null, false)

        val ingredientNameLayout = dialogView.findViewById<TextInputLayout>(R.id.ingredientNameInputLayout)
        val amountLayout = dialogView.findViewById<TextInputLayout>(R.id.ingredientAmountInputLayout)
        val nameEditText = dialogView.findViewById<TextInputEditText>(R.id.ingredientNameEditText)
        val amountEditText = dialogView.findViewById<TextInputEditText>(R.id.ingredientAmountEditText)
        val saveButton = dialogView.findViewById<Button>(R.id.saveIngredientButton)
        val cancelButton = dialogView.findViewById<Button>(R.id.cancelIngredientButton)

        saveButton.setOnClickListener {
            val name = nameEditText.text?.toString()?.trim()
            val amount = amountEditText.text?.toString()?.trim()

            ingredientNameLayout.error = null
            amountLayout.error = null

            if (!name.isNullOrEmpty()
                && !amount.isNullOrEmpty()
                ) {
                val newIngredient = Ingredient(
                    name = name,
                    amount = amount
                )
                (targetFragment as? AddItemListener)?.onIngredientAdded(newIngredient)
                dismiss()
            } else {
                if (name.isNullOrEmpty()) {
                    ingredientNameLayout.error = "Ingredient name cannot be empty"
                }
                if (amount.isNullOrEmpty()) {
                    amountLayout.error = "Ingredient amount cannot be empty"
                }
            }
        }

        cancelButton.setOnClickListener {
            dismiss()
        }


        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)

        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        return dialog
    }
}