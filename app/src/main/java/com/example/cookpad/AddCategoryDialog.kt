package com.example.cookpad

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import android.widget.Button
import android.widget.TextView

class AddCategoryDialog : DialogFragment() {

    // Define the interface to communicate the result back to the CategoriesFragment
    interface AddCategoryListener {
        fun onCategoryAdded(categoryName: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Return null here to indicate that we will create the dialog in onCreateDialog
        return null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.popup_add_category, null, false)

        // --- 1. View Bindings ---
        val categoryNameEditText = dialogView.findViewById<TextInputEditText>(R.id.categoryNameEditText)
        val saveButton = dialogView.findViewById<Button>(R.id.saveCategoryButton)
        val cancelButton = dialogView.findViewById<Button>(R.id.cancelCategoryButton)

        // --- 2. Setup Click Listeners ---

        // SAVE Button Listener
        saveButton.setOnClickListener {
            val categoryName = categoryNameEditText.text?.toString()?.trim()

            if (!categoryName.isNullOrEmpty()) {
                // Communicate the new category name back to the CategoriesFragment
                (targetFragment as? AddCategoryListener)?.onCategoryAdded(categoryName)
                dismiss() // Close the dialog
            } else {
                // Optional: Show an error message (e.g., set an error on the TextInputLayout)
            }
        }

        // CANCEL Button Listener
        cancelButton.setOnClickListener {
            dismiss() // Close the dialog
        }

        // --- 3. Build the AlertDialog ---
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)

        // NOTE: Since your layout includes the title, you can omit setTitle on the builder,
        // but if you want to apply the standard dialog style, the builder is required.

        val dialog = builder.create()

        // Optional: Ensure the background/corners are respected if the AlertDialog wrapper interferes
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        return dialog
    }
}