package com.example.cookpad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

// STEP 1: Implement the Filterable interface
class CategoryAdapter(
    // This list will now be the one that gets modified for display
    private var categories: MutableList<Category>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(), Filterable {

    // A separate list to hold the original, unfiltered data
    private var originalCategories: List<Category> = ArrayList(categories)

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.categoryNameTextView)
        private val imageView: ImageView = itemView.findViewById(R.id.categoryImageView)
        private val previewTextView: TextView = itemView.findViewById(R.id.recipePreviewTextView)

        fun bind(category: Category) {
            nameTextView.text = category.name

            Glide.with(itemView.context)
                .load(category.image)
                .placeholder(R.drawable.ic_image)
                .into(imageView)

            if (category.previewRecipeNames.isNotEmpty()) {
                previewTextView.visibility = View.VISIBLE
                previewTextView.text = category.previewRecipeNames.joinToString(", ")
            } else {
                previewTextView.visibility = View.GONE
            }

            itemView.setOnClickListener {
                onItemClick(category.name)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        // Your layout name might be different, e.g., list_item_category.
        // I'm using category_item from your code.
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount() = categories.size

    // STEP 2: Implement the getFilter() method to provide the filtering logic
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<Category>()
                if (constraint.isNullOrEmpty()) {
                    // If search is empty, show the original full list
                    filteredList.addAll(originalCategories)
                } else {
                    val filterPattern = constraint.toString().lowercase().trim()
                    // Filter the original list based on the category name
                    originalCategories.forEach { category ->
                        if (category.name.lowercase().contains(filterPattern)) {
                            filteredList.add(category)
                        }
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                // Clear the current display list
                categories.clear()
                // Add the filtered results
                if (results?.values is List<*>) {
                    categories.addAll(results.values as List<Category>)
                }
                // Notify the adapter to refresh the RecyclerView
                notifyDataSetChanged()
            }
        }
    }
}
