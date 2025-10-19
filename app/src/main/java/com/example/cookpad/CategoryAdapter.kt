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

class CategoryAdapter(
    private var categories: MutableList<Category>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(), Filterable {

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
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount() = categories.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<Category>()
                if (constraint.isNullOrEmpty()) {
                    filteredList.addAll(originalCategories)
                } else {
                    val filterPattern = constraint.toString().lowercase().trim()
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
                categories.clear()
                if (results?.values is List<*>) {
                    categories.addAll(results.values as List<Category>)
                }
                notifyDataSetChanged()
            }
        }
    }
}
