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

    override fun onStart() {
        super.onStart()

        dialog?.window?.let { window ->
            // dialog width is a percentage of screen
            val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
            window.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    interface AddCategoryListener {
        fun onCategoryAdded(categoryName: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.popup_add_category, null, false)

        val categoryNameEditText = dialogView.findViewById<TextInputEditText>(R.id.categoryNameEditText)
        val saveButton = dialogView.findViewById<Button>(R.id.saveCategoryButton)
        val cancelButton = dialogView.findViewById<Button>(R.id.cancelCategoryButton)

        saveButton.setOnClickListener {
            dismiss()
//            val categoryName = categoryNameEditText.text?.toString()?.trim()
//
//            if (!categoryName.isNullOrEmpty()) {
//                (targetFragment as? AddCategoryListener)?.onCategoryAdded(categoryName)
//                dismiss() // Close the dialog
//            } else {
//
//            }
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