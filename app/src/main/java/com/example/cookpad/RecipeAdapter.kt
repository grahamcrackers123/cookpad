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

// 2. IMPLEMENT the Filterable interface
class RecipeAdapter(
    // 3. This list is now MUTABLE to hold the filtered results
    private var recipes: MutableList<Recipe>,
    private val onItemClick: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>(), Filterable {

    // 4. A NEW LIST to hold the original, unfiltered data
    private var originalRecipes: List<Recipe> = ArrayList(recipes)

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.recipeName)
        private val imageView: ImageView = itemView.findViewById(R.id.recipeImage)

        fun bind(recipe: Recipe) {
            nameTextView.text = recipe.title

            // Use Glide for better image loading
            Glide.with(itemView.context)
                .load(recipe.image)
                .placeholder(R.drawable.ic_image) // A default image while loading
                .into(imageView)

            itemView.setOnClickListener {
                onItemClick(recipe)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemCount() = recipes.size

    // 5. IMPLEMENT the getFilter() method to provide filtering logic
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<Recipe>()
                if (constraint.isNullOrEmpty()) {
                    // If search is empty, show the original full list
                    filteredList.addAll(originalRecipes)
                } else {
                    val filterPattern = constraint.toString().lowercase().trim()
                    // Filter the original list based on the recipe title
                    originalRecipes.forEach { recipe ->
                        if (recipe.title.lowercase().contains(filterPattern)) {
                            filteredList.add(recipe)
                        }
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                // Clear the current display list
                recipes.clear()
                // Add the filtered results
                if (results?.values is List<*>) {
                    recipes.addAll(results.values as List<Recipe>)
                }
                // Notify the adapter to refresh the RecyclerView
                notifyDataSetChanged()
            }
        }
    }
}
